package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransactionDao;
import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.dto.MailMessageDto;
import com.bcp.mdp.dto.TarificationOfTransaction;
import com.bcp.mdp.dto.TransferDto;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

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

	@Override
	public void createTransaction(TransferDto transfer) {
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
		/*commissionService.persist(commission);
		transactionForPersistance.setCommission(commission);*/
		transactionForPersistance.setApplyCommission(transfer.isApplyCommission());
		transactionForPersistance.setSource(transferSourceService.retrieveByCode(transfer.getTransactionSource()));
		transactionForPersistance.setChargeType(transfer.getChargeType());
		transferDao.save(transactionForPersistance);
	}
	
	@Override
	public void createIntermediaireTransaction(long debitAccount, long creditAccount, double commission) {
		
	}
	
	@Override
	public String doTransfer( TransferDto transfer) {
		// TODO Auto-generated method stub
		long debitAccountNumber=transfer.getPrincipalAccount();
		long creditAccountNumber=transfer.getBeneficiaryAccount();
		
		
		// verifier s'il doit avoir échange
		String currency=transfer.getTransactionCurrency();
		String currencyDebit=accountService.retrieveAccountCurrency(debitAccountNumber);
		String currencyCredit=accountService.retrieveAccountCurrency(creditAccountNumber);
		if(!currency.equals(AppConstants.LOCAL_CURRENCY)
				|| !currency.equals(currencyDebit)
				|| !currency.equals(currencyCredit)) {
			
		}
		
		double amount=transfer.getTransactionAmount();
		double comm=transfer.getCommissionAmount();
		double tva=transfer.getCommissionTVA();
		double sumAmount=amount+comm+tva;
		if((accountService.retrieveFreeBalanceByAccountNumber(debitAccountNumber)>=sumAmount
			&& transfer.getChargeType().equals("OUR")) || accountService.retrieveFreeBalanceByAccountNumber(debitAccountNumber)>=amount ) {
			createTransaction(transfer);
			
			if(transfer.getChargeType().equals("OUR"))
			{
				accountService.addObligation(debitAccountNumber, sumAmount);
			}
			
			else
			{
				accountService.addObligation(debitAccountNumber, amount);
			}
			return "OK";
		}
		
		else {
			if(accountService.retrieveBalanceByAccountNumber(debitAccountNumber)>=sumAmount) {
				
				return "Vous avez  déjà des transactions en attente";
			}
		}
		
		return "Solde insuffisant";
	}

	/*@Override
	public void doTransfer( TransferDto transfer) {
		// TODO Auto-generated method stub
		long debitAccountNumber=transfer.getPrincipalAccount();
		long creditAccountNumber=transfer.getBeneficiaryAccount();
		double amount=transfer.getTransactionAmount();

		TransferType transferType=tarificationService.verifyTransferType(debitAccountNumber, creditAccountNumber);
		TarificationOfTransaction tarification=tarificationService.retrieveTarification(transferType, amount);

		if(accountService.retrieveBalanceByAccountNumber(debitAccountNumber)>=amount+tarification.getSumAmount()) {

			String instituteReferenceForDebitAccount=accountService.retrieveAccountResidenceReference(debitAccountNumber);
			accountService.debitAccount(debitAccountNumber, amount+tarification.getSumAmount());
			accountService.creditAccount(accountService.retrieveInstituteAccountNumberPLByReferenceOfInstitut(instituteReferenceForDebitAccount),tarification.getCommissionAmount() );
			accountService.creditAccount(accountService.retrieveInstituteAccountNumberTVAByReferenceOfInstitut(instituteReferenceForDebitAccount),tarification.getTvaAmount() );
			if(transferType.equals(transferTypeDao.findByType("InterBpr"))) {

				String instituteReferenceForCreditAccount=accountService.retrieveAccountResidenceReference(creditAccountNumber);
				Long bprLinkaccountDebtit=accountService.retrieveBprLinkAccount(instituteReferenceForDebitAccount);
				Long bprLinkaccountCredit=accountService.retrieveBprLinkAccount(instituteReferenceForCreditAccount);


				accountService.creditAccount(bprLinkaccountDebtit, amount);
				createTransaction(transfer,debitAccountNumber,bprLinkaccountDebtit);

				accountService.debitAccount(bprLinkaccountDebtit, amount);
				accountService.creditAccount(bprLinkaccountCredit, amount);
				createTransaction(transfer,bprLinkaccountDebtit,bprLinkaccountCredit);

				accountService.debitAccount(bprLinkaccountCredit, amount);
				accountService.creditAccount(creditAccountNumber, amount);
				createTransaction(transfer,bprLinkaccountCredit,creditAccountNumber );

			}
			else {


			accountService.creditAccount(creditAccountNumber, amount);
			createTransaction(transfer,debitAccountNumber,creditAccountNumber);

				accountService.creditAccount(creditAccountNumber, amount);
				createTransaction(transfer,debitAccountNumber,creditAccountNumber);

			}

		}

	}*/
	@Override
	public void executeTransaction(Transaction transaction) throws MessagingException, IOException {
		double amount=transaction.getAmount();
		double commission=amount*transaction.getCommission().getCommissionRate();
		double tva=commission*transaction.getCommission().getTvaRate();
		double sumAmount=amount+commission+tva;
		
		double obligation=0;
		if(transaction.isApplyCommission()==true)
		{

			if(transaction.getChargeType().equals("OUR"))
			{
				accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),sumAmount);
				accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount);
				obligation=sumAmount;
			}
			else
			{
				accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),amount);
				accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount-(commission+tva));
				obligation=amount;
			}
				
			String instituteReferenceForDebitAccount=accountService.retrieveAccountResidenceReference(transaction.getDebitAccount().getAccountNumber());
			accountService.creditAccount(accountService.retrieveInstituteAccountNumberPLByReferenceOfInstitut(instituteReferenceForDebitAccount),commission);
			accountService.creditAccount(accountService.retrieveInstituteAccountNumberTVAByReferenceOfInstitut(instituteReferenceForDebitAccount),tva);
			if(transaction.getReference().startsWith("3", 3))
			{
				
				String instituteReferenceForCreditAccount=accountService.retrieveAccountResidenceReference(transaction.getCreditAccount().getAccountNumber());
				Long bprLinkaccountDebtit=accountService.retrieveBprLinkAccount(instituteReferenceForDebitAccount);
				Long bprLinkaccountCredit=accountService.retrieveBprLinkAccount(instituteReferenceForCreditAccount);
				
				
				accountService.creditAccount(bprLinkaccountDebtit, amount);
				createIntermediaireTransaction(transaction.getDebitAccount().getAccountNumber(),bprLinkaccountDebtit,amount);
				
				accountService.debitAccount(bprLinkaccountDebtit, amount);
				accountService.creditAccount(bprLinkaccountCredit, amount);
				createIntermediaireTransaction(bprLinkaccountDebtit,bprLinkaccountCredit,amount);
				
				accountService.debitAccount(bprLinkaccountCredit, amount);
				accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount);
				createIntermediaireTransaction(bprLinkaccountCredit,transaction.getCreditAccount().getAccountNumber(),amount);
				
		}
		}
		
		else
		{
			accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),amount);
			obligation=amount;
			accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount);
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

}
