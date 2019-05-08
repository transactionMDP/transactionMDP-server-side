package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransactionDao;
import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.dto.MailMessageDto;
import com.bcp.mdp.dto.TransferRequest ;
import com.bcp.mdp.exception.AppException;
import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Commission;
import com.bcp.mdp.model.Currency;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.model.TransferType;
import com.bcp.mdp.security.UserPrincipal;
import com.bcp.mdp.util.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.persistence.Column;

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
    private  ITransferSourceService  transferSourceService;
    
    @Autowired
    private IExchangeService exchangeService;
    
    @Autowired
    private IAutorisationService autorisationService;

	@Override
	public void createTransaction(TransferRequest  transfer, Commission commission) {
		// TODO Auto-generated method stub
		Transaction transactionForPersistance =new Transaction() ;
		Account debitAccount=accountService.retrieveAccountByAccountNumber(transfer.getPrincipalAccount());
		Account creditAccount=accountService.retrieveAccountByAccountNumber(transfer.getBeneficiaryAccount());
		transactionForPersistance.setDebitAccount(debitAccount);
		transactionForPersistance.setCreditAccount(creditAccount);
		transactionForPersistance.setAmount(transfer.getTransactionAmount());
		transactionForPersistance.setExecutionDate(transfer.getExecutionDate());
		transactionForPersistance.setTransferReason(transfer.getTransferReason());
		transactionForPersistance.setType(tarificationService.verifyTransferType(transfer.getPrincipalAccount(),transfer.getBeneficiaryAccount()));
		transactionForPersistance.setTransferNature(transfer.getTransferNature());
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
	public String doTransfer(UserPrincipal currentUser,  TransferRequest  transfer) {
		// TODO Auto-generated method stub
		long debitAccountNumber=transfer.getPrincipalAccount();
		long creditAccountNumber=transfer.getBeneficiaryAccount();
		// verifier que les comptes st des comptes clients à part pour le canaux ctn et fonction centrale
		
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
		double sumAmountToMAD=sumAmount*exchangeService.valorisation(debitAccountNumber, sumAmount, currency, "MAD");
		Commission commission = new Commission();
		commission.setCommissionRate(transfer.getCommissionRate());
		commission.setTvaRate(transfer.getTVARate());
		
		long autorisationNumber=transfer.getAutorisationNumber();
		
		
		// valoriser la devise de transaction vers le MAD (pour la commission) et vers la devise (credit ou debit) qui est differente de celle de la transaction
		if(currency.equals(currencyDebit)==false||currency.equals(currencyCredit)==false)
		{
			// il faut d'avoir verifier l'autorisation de l'office de change. ? pour le debiteur oubien pour les deux
			
			if(autorisationNumber!=0)
			{
				System.out.print(autorisationService.check(autorisationNumber, transfer.getAutorisationValidate(), debitAccountNumber));
				
				if(autorisationService.check(autorisationNumber, transfer.getAutorisationValidate(), debitAccountNumber)==null)
				{
					
					return "non autorisé par l'office des changes";
				}
				
				else
				{
					if(autorisationService.getFreeBalance(autorisationNumber)<sumAmountToMAD)
					{
						return "solde de l'autorisation insuffissante";
					}
				}
			}
		
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
				if(isTransactionCurrencyEqualsDebitCurrency==false&&accountDebitFreeBalance>=sumAmountExchange)
				{
					accountService.addObligation(debitAccountNumber,sumAmountExchange);
					debitOK=true;
				}
					
			}
		}

		
		else  // c'est à dire la charge est BEN ( charge endossée par le crediteur)
		{
				if(isTransactionCurrencyEqualsDebitCurrency&&accountDebitFreeBalance>=amount)
				{
					accountService.addObligation(debitAccountNumber, amount);
					debitOK=true;
				}
				
				else
				{
					if(isTransactionCurrencyEqualsDebitCurrency==false&&accountDebitFreeBalance>=amountExchange)
					{
						 accountService.addObligation(debitAccountNumber, amountExchange);
						 debitOK=true;
					}
				}
					
		
			}

		if(debitOK)
		{
			createTransaction(transfer,commission);
			autorisationService.updateObligation(autorisationNumber, sumAmountToMAD);
			return "OK";
		}
			return "Solde insuffisant";
	}

	@Override
	public void executeTransaction(Transaction transaction) throws MessagingException, IOException {
		 comptabiliser(transaction);
		// updateTransactionState(transaction.getReference(), "6000"); // on va plutot utiliser le save  car une transaction n'existe pas toujours dans a BD
		 transaction.setState(stateService.retrieveStateByLibelle("6000"));// on met à jour l'objet qu'on a en meme temps
		 transferDao.save(transaction);// les transfer d'autres canaux (flux de masse par exemple) ne sont pas persistées au préalable
		 if(transaction.getAutorisationNumber()!=0)
		 {
		 double amount=transaction.getAmount();
				
			double commission=amount*transaction.getCommission().getCommissionRate();
			double tva=commission*transaction.getCommission().getTvaRate();
			double sumAmount=amount+commission+tva;
			double sumAmountToMAD=sumAmount*transaction.getCommission().getExchangeTransferCurrencyToMAD();
		    autorisationService.updateObligation(transaction.getAutorisationNumber(), -sumAmountToMAD );
		    autorisationService.updateBalance(transaction.getAutorisationNumber(), -sumAmountToMAD);
		 }
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
    
    @Override
    public void notifierTransaction() {
    	
    }
    @Override
    //pour ne pas repeter la comptabilisation lors de la création et l'annulation du virement
    public void comptabiliser(Transaction transaction)
    {
    	double amount=transaction.getAmount();
		
		double commission=amount*transaction.getCommission().getCommissionRate();
		double tva=commission*transaction.getCommission().getTvaRate();
		double sumAmount=amount+commission+tva;
		
		boolean transactionCurrencyEqualsDebitCurrency=true;//transaction.getCommission().isTransactionCurrencyEqualsDebitCurrency();
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
		
		if(transaction.getState().getCode().equals("5000")==false) // si l'état  de la transaction est autre que  annuléé
		{
			accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(),obligation);
		}
		
		
		 
    }

    @Override
    public void cancel(Transaction transaction)
    {
    	double amount=transaction.getAmount();	
		double commission=amount*transaction.getCommission().getCommissionRate();
		double tva=commission*transaction.getCommission().getTvaRate();
		double sumAmount=amount+commission+tva;
		double sumAmountToMAD=sumAmount*transaction.getCommission().getExchangeTransferCurrencyToMAD();
	
    	if(transaction.getState().getCode().equals("6000")) // si la transaction avant été exécuté
    	{
    		// permutation du compte debiteur et du compte crediteur afin de faire une comptabilisation inverse
    		Account debitAccount=transaction.getCreditAccount();
    		Account creditAccount=transaction.getDebitAccount();
        	
    		transaction.setCreditAccount(creditAccount);
    		transaction.setDebitAccount(debitAccount);
   
    		comptabiliser(transaction);
    		
    		 if(transaction.getAutorisationNumber()!=0)
    		 {
    		
    		    autorisationService.updateBalance(transaction.getAutorisationNumber(), +sumAmountToMAD);
    		 }
    	}
    	
    	else
    	{
    		 if(transaction.getAutorisationNumber()!=0)
    		 {
    		
    		    autorisationService.updateObligation(transaction.getAutorisationNumber(), -sumAmountToMAD );
    		 }
    		 
    		 if(transaction.getTransactionCurrency().equals(transaction.getDebitAccount().getAccountCurrency()))
    		 {
    			 if(transaction.getChargeType().equals("OUR"))
    			 {
    				 accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(),sumAmount);
    			 }
    			 else
    			 {
    				 accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(),amount);
    			 }
    		 }
    		 
    		 else
    		 {
    			 if(transaction.getChargeType().equals("OUR"))
    			 {
    				 accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(),sumAmount*transaction.getCommission().getExchangeTransferCurrencyToOther());
    			 }
    			 
    			 else
    			 {
    				 accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(),amount*transaction.getCommission().getExchangeTransferCurrencyToOther());
    			 }
    			
    		 }
    		 
    		
    	}
    	//changer l'etat de la transaction en annulée
    	updateTransactionState(transaction.getReference(), "5000"); // 5000 c'est le code de l'état annulée
    		
    }

	@Override
	public void storeFile(MultipartFile file, String username) {

	}
}
