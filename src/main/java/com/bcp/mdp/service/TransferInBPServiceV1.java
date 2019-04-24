package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransactionDao;
import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.dto.TarificationOfTransaction;
import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Currency;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private CurrencyServiceV1 currencyService;
	
	@Autowired
	private StateServiceV1 stateService;

	@Override
	public void createTransaction(TransferDto transfer, long debit, long credit) {
		// TODO Auto-generated method stub
		Transaction transactionForPersistance =new Transaction() ;
		transactionForPersistance.setOperationDate(new Date());
		Account debitAccount=accountService.retrieveAccountByAccountNumber(debit);
		Account creditAccount=accountService.retrieveAccountByAccountNumber(credit);
		transactionForPersistance.setDebitAccount(debitAccount);
		transactionForPersistance.setCreditAccount(creditAccount);
		transactionForPersistance.setAmount(transfer.getTransactionAmount());
		transactionForPersistance.setExecutionDate(transfer.getExecutionDate());
		transactionForPersistance.setTransferReason(transfer.getTransferReason());
		Currency currency= currencyService.retrieveCurrencyByName(transfer.getTransactionCurrency());
		transactionForPersistance.setTransactionCurrency(currency);
		transactionForPersistance.setTypeTransferSource("intra en agence");
		transactionForPersistance.setState(stateService.retrieveStateByLibelle("1000"));
		transferDao.save(transactionForPersistance);
	}
	
	@Override
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
			}
			
		}

	}
	
	@Override
	public void executeTransaction(Transaction transaction) {
		
	}
	
	@Override
	public void updateTransactionState(String transactionRef, String codeState) {
		
		transferDao.updateTransactionState(transactionRef, codeState);
		
	}
	
	public List<Transaction> retrieveTransactionsToExecuteToday(){
		return transferDao.findByExecutionDateAndExecuted(new Date(), false);
		
	}
	@Override
	public void  doTransactionOfExecutionDayToday(List<Transaction> transactions) {
		// this function execute(debit and credit) transaction whom ecécuteDay equals today

		for(Transaction transaction: transactions)
		{
			 executeTransaction(transaction);
		}
		
	}
	
	@Override
	public Transaction retrieveByReference(String reference) {
		return transferDao.findByReference(reference);
	}

	@Override
	public void tariffyTransfer() {
		// TODO Auto-generated method stub

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
	public List<Transaction> retrieveTransactionDoByTeller(long TellerRegistrationNumber) {
		// TODO Auto-generated method stub
		return null;
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
	public void doTransactionOfExecutionDayToday() {
		// TODO Auto-generated method stub
		
	}
}
