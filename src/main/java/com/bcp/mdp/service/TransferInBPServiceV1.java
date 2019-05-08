package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransactionDao;
import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.dto.MailMessageDto;
import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.exception.AppException;
import com.bcp.mdp.exception.FileStorageException;
import com.bcp.mdp.model.*;
import com.bcp.mdp.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component("transferService")
public class TransferInBPServiceV1 implements ITransferInBPService {

    @Autowired
    private TransactionDao transferDao;

    @Autowired
    private TransferTypeDao transferTypeDao;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ITarificationService tarificationService;
    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private IStateService stateService;

    @Autowired
    private ICommissionService commissionService;
    
    @Autowired
    private ITransferSourceService transferSourceService;
    
    @Autowired
    private IExchangeService exchangeService;

	@Override
	public void createTransaction(TransferDto transfer, Commission commission) {
		// TODO Auto-generated method stub
		Transaction transactionForPersistance =new Transaction() ;
		Account debitAccount=accountService.retrieveAccountByAccountNumber(transfer.getPrincipalAccount());
		Account creditAccount=accountService.retrieveAccountByAccountNumber(transfer.getBeneficiaryAccount());
		transactionForPersistance.setDebitAccount(debitAccount);
		transactionForPersistance.setCreditAccount(creditAccount);
		transactionForPersistance.setAmount(transfer.getTransactionAmount());
		transactionForPersistance.setExecutionDate(transfer.getExecutionDate());
		transactionForPersistance.setTransferReason(transfer.getTransferReason());
		Currency currency= currencyService.retrieveCurrencyByCode(transfer.getTransactionCurrency());
		transactionForPersistance.setTransactionCurrency(currency);
		transactionForPersistance.setSource(transferSourceService.retrieveByCode(transfer.getTransactionSource()));
		transactionForPersistance.setState(stateService.retrieveStateByLibelle("1000"));
		commissionService.persist(commission);
		transactionForPersistance.setCommission(commission);
		transactionForPersistance.setApplyCommission(transfer.isApplyCommission());
		transactionForPersistance.setSource(transferSourceService.retrieveByCode(transfer.getTransactionSource()));
		transactionForPersistance.setChargeType(transfer.getChargeType());
		transferDao.save(transactionForPersistance);
	}
	
	@Override
	public void createIntermediaireTransaction(long debitAccount, long creditAccount, double commission) {
		
	}
	
	@Override
	public String doTransfer(UserPrincipal currentUser, TransferDto transfer) {
		// TODO Auto-generated method stub
		long debitAccountNumber=transfer.getPrincipalAccount();
		long creditAccountNumber=transfer.getBeneficiaryAccount();
		
		// ces variables servent à verifier s'il doit avoir échange ou pas 
		String currency=transfer.getTransactionCurrency();
		String currencyDebit=accountService.retrieveAccountCurrency(debitAccountNumber);
		String currencyCredit=accountService.retrieveAccountCurrency(creditAccountNumber);

		transfer.setTransferNature(currencyDebit+"_"+currencyCredit);

		currentUser.getAuthorities().stream()
				.filter(authority -> transfer.getTransferNature().equals(authority.getAuthority()))
				.findAny()
				.orElseThrow(() -> new AppException("Permission denied."));
		
		boolean isTransactionCurrencyEqualsDebitCurrency=currency.equals(currencyDebit);
		
		double amount=transfer.getTransactionAmount();
		double comm=transfer.getCommissionAmount();
		double tva=transfer.getCommissionTVA();
		double sumAmount;
		if(transfer.isApplyCommission()==true)
		{
			sumAmount=amount+comm+tva;
		}
		else
		{
			sumAmount=amount;
		}
		
		Commission commission = new Commission();
		commission.setCommissionRate(transfer.getCommissionRate());
		commission.setTvaRate(transfer.getTVARate());
		
		
		
		// valoriser la devise de transaction vers le MAD (pour la commission) et vers la devise (credit ou debit) qui est differente de celle de la transaction
		if(currency.equals(currencyDebit)==false&&currency.equals(currencyCredit)==false)
		{
		
			if(isTransactionCurrencyEqualsDebitCurrency)
			{
				 commission.setExchangeTransferCurrencyToOther(exchangeService.valorisation(debitAccountNumber, amount, currency, currencyCredit));
			}
			
			else
			{
				 commission.setExchangeTransferCurrencyToOther(exchangeService.valorisation(debitAccountNumber, amount, currency, currencyDebit));
			}
			commission.setExchangeTransferCurrencyToMAD( exchangeService.valorisation(debitAccountNumber, comm, currency, "MAD"));
		}
	    
	    double accountDebitFreeBalance=accountService.retrieveFreeBalanceByAccountNumber(debitAccountNumber);
	    double amountExchange=amount *commission.getExchangeTransferCurrencyToOther();
	    double sumAmountExchange=sumAmount *commission.getExchangeTransferCurrencyToOther();
	    
	    boolean debitOK=false;
		if( transfer.getChargeType().equals("OUR"))   // la charge est OUR signifie que charge endossée par le debiteur)
		{
			

			if(isTransactionCurrencyEqualsDebitCurrency&&accountDebitFreeBalance>=sumAmount)
			{
					accountService.addObligation(debitAccountNumber, sumAmount);
					debitOK=true;
			}
				
			else
			{
				if(transfer.isTransactionCurrencyEqualsDebitCurrency()==false&&accountDebitFreeBalance>=sumAmountExchange)
				{
					accountService.addObligation(debitAccountNumber,sumAmountExchange);
					debitOK=true;
				}
					
			}
		}

		
		else  // c'est à dire la charge est BEN ( charge endossée par le crediteur)
		{
				if(transfer.isTransactionCurrencyEqualsDebitCurrency()&&accountDebitFreeBalance>=amount)
				{
					accountService.addObligation(debitAccountNumber, amount);
					debitOK=true;
				}
				
				else
				{
					if(transfer.isTransactionCurrencyEqualsDebitCurrency()==false&&accountDebitFreeBalance>=amountExchange)
					{
						 accountService.addObligation(debitAccountNumber, amount*transfer.getExchangeTransferCurrencyToOther());
						 debitOK=true;
					}
				}
					
		
			}

		if(debitOK)
		{
			createTransaction(transfer,commission);
			return "OK";
		}
			return "Solde insuffisant";
	}

	@Override
	public void executeTransaction(Transaction transaction) throws MessagingException, IOException {
		double amount=transaction.getAmount();
		double commission=amount*transaction.getCommission().getCommissionRate();
		double tva=commission*transaction.getCommission().getTvaRate();
		double sumAmount=amount+commission+tva;
		
		boolean transactionCurrencyEqualsDebitCurrency=transaction.getCommission().isTransactionCurrencyEqualsDebitCurrency();
		double exchangeTransferCurrencyToOther=transaction.getCommission().getExchangeTransferCurrencyToOther();
		double exchangeTransferCurrencyToMAD=transaction.getCommission().getExchangeTransferCurrencyToMAD();
		
		double obligation=0;
		if(transaction.isApplyCommission()==true)
		{

			if(transaction.getChargeType().equals("OUR"))
			{
				if(transactionCurrencyEqualsDebitCurrency)
				{
					accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),sumAmount);
					accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount* exchangeTransferCurrencyToOther);
					obligation=sumAmount;
				}
				else
				{
					accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),sumAmount* exchangeTransferCurrencyToOther);
					accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount);
					obligation=sumAmount* exchangeTransferCurrencyToOther;
				}
				
				
			}
			else
			{
				if(transactionCurrencyEqualsDebitCurrency)
				{
					accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),amount);
					accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), (amount-(commission+tva))* exchangeTransferCurrencyToOther);
					obligation=amount;
				}
				else
				{
					accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),amount* exchangeTransferCurrencyToOther);
					accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount-(commission+tva));
					obligation=amount* exchangeTransferCurrencyToOther;
				}
			}
				
			String instituteReferenceForDebitAccount=accountService.retrieveAccountResidenceReference(transaction.getDebitAccount().getAccountNumber());
			accountService.creditAccount(accountService.retrieveInstituteAccountNumberPLByReferenceOfInstitut(instituteReferenceForDebitAccount),commission*exchangeTransferCurrencyToMAD);
			accountService.creditAccount(accountService.retrieveInstituteAccountNumberTVAByReferenceOfInstitut(instituteReferenceForDebitAccount),tva*exchangeTransferCurrencyToMAD);
			if(transaction.getReference().startsWith("3", 3))
			{
				
				String instituteReferenceForCreditAccount=accountService.retrieveAccountResidenceReference(transaction.getCreditAccount().getAccountNumber());
				Long bprLinkaccountDebtit=accountService.retrieveBprLinkAccount(instituteReferenceForDebitAccount);
				Long bprLinkaccountCredit=accountService.retrieveBprLinkAccount(instituteReferenceForCreditAccount);
				
				
				accountService.creditAccount(bprLinkaccountDebtit, amount*exchangeTransferCurrencyToMAD);
				createIntermediaireTransaction(transaction.getDebitAccount().getAccountNumber(),bprLinkaccountDebtit,amount*exchangeTransferCurrencyToMAD);
				
				accountService.debitAccount(bprLinkaccountDebtit, amount*exchangeTransferCurrencyToMAD);
				accountService.creditAccount(bprLinkaccountCredit, amount*exchangeTransferCurrencyToMAD);
				createIntermediaireTransaction(bprLinkaccountDebtit,bprLinkaccountCredit,amount*exchangeTransferCurrencyToMAD);
				
				accountService.debitAccount(bprLinkaccountCredit, amount*exchangeTransferCurrencyToMAD);
				accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount*exchangeTransferCurrencyToMAD);
				createIntermediaireTransaction(bprLinkaccountCredit,transaction.getCreditAccount().getAccountNumber(),amount*exchangeTransferCurrencyToMAD);
				
		}
		}
		
		else
		{
			if(transactionCurrencyEqualsDebitCurrency)
			{
				accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),amount);
				obligation=amount;
				accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount*exchangeTransferCurrencyToOther);
			}
			
			else
			{
				accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),amount*exchangeTransferCurrencyToOther);
				obligation=amount*exchangeTransferCurrencyToOther;
				accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount);
			}
		}
		
		accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(),obligation);
		 updateTransactionState(transaction.getReference(), "6000");
		 
		 MailMessageDto mail= new MailMessageDto();
		 String nameDebiter=accountService.retrieveAccountCustomerName(transaction.getDebitAccount().getAccountNumber());
		 String emailDebiter=accountService.retrieveAccountCustomerEmail(transaction.getDebitAccount().getAccountNumber());
		 String nameCrediter=accountService.retrieveAccountCustomerName(transaction.getCreditAccount().getAccountNumber());
		 String emailCrediter=accountService.retrieveAccountCustomerEmail(transaction.getCreditAccount().getAccountNumber());
		 
		 mail.setNameOfCreditor(nameCrediter);
		 mail.setNameOfDebitor(nameDebiter);
		 mail.setToCreditAccount(emailCrediter);
		 mail.setToDebitAccount(emailDebiter);
		 mail.setAmountOfTransaction(transaction.getAmount());
		 
		 MailNotificationService.sendSimpleMessage(mail);
		
	}
	

	@Override
	public List<Transaction> retrieveTransactionsToExecuteToday(LocalDate date){
		return transferDao.transactionToExecuteToday(date);
		
	}
	@Override
	public void  doTransactionOfExecutionDayToday(List<Transaction> transactions) throws MessagingException, IOException {
		// this function execute(debit and credit) transaction whom ecécuteDay equals today

		for(Transaction transaction: transactions)
		{
			 executeTransaction(transaction);
		}
		
	}

    @Override
    public void updateTransactionState(String transactionRef, String codeState) {

        transferDao.updateTransactionState(transactionRef, codeState);

    }

    /*public List<Transaction> retrieveTransactionsToExecuteToday(){
        return transferDao.findByExecutionDateAndExecuted(new Date(), false);

    }*/

    @Override
    public Transaction retrieveByReference(String reference) {
        return transferDao.findByReference(reference);
    }

    @Override
    public void tariffyTransfer() {
        // TODO Auto-generated method stub
    }

	
	@Override
	public List<Transaction>/*PagedResponse<Transaction>*/ getUserTransfers(String currentUser, int page, int size) {
		//validatePageNumberAndSize(page, size);
		return transferDao.findByCreatedBy(currentUser);
	}

    

    @Override
    public void accountTransfer() {
        // TODO Auto-generated method stub

    }

    @Override
    public Transaction checkTransfer(long transactionId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transaction retrieveTransfer(long transactionId) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Transaction> retrieveTransfers() {
        // TODO Auto-generated method stub
        return transferDao.findAll();
    }

  
    @Override
    public List<Transaction>/*PagedResponse<Transaction>*/ getTransfersByState(String stateCode, int page, int size) {
        return transferDao.findByState(stateCode);
    }

    @Override
    public List<Transaction> retrieveTransactionDoInInstitute(String instituteReference) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Transaction> retrieveTransactionDoAtOperationDate(Date date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Transaction> retrieveTransactionDoAtOperationDateOnAccount(long accountId, Date date) {
        // TODO Auto-generated method stub
        return null;
    }



	//@Value("${app.uploadDir}")
	String fileStorageProperties;
	@Override
	public /*User*/void storeFile(MultipartFile file, String username) {
		//User userFileStorageProperties=getUserByUsername(username);

        /*this.fileStorageLocation = Paths.get(fileStorageProperties)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }*/
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			// file.getContentType()
			/*User userAvatar = getUserByUsername(username);
			userAvatar.setAvatar(file.getBytes());

			return userRepository.save(userAvatar);*/
		} catch (Exception ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

}
