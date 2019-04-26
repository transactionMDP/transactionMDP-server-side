package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransactionDao;
import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.dto.TarificationOfTransaction;
import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Commission;
import com.bcp.mdp.model.Currency;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.model.TransferType;
import com.bcp.mdp.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@Override
	public void createTransaction(TransferDto transfer,Commission commission,TransferType transferType) {
		// TODO Auto-generated method stub
		Transaction transactionForPersistance =new Transaction() ;
		Account debitAccount=accountService.retrieveAccountByAccountNumber(transfer.getPrincipalAccount());
		Account creditAccount=accountService.retrieveAccountByAccountNumber(transfer.getBeneficiaryAccount());
		transactionForPersistance.setDebitAccount(debitAccount);
		transactionForPersistance.setCreditAccount(creditAccount);
		transactionForPersistance.setAmount(transfer.getTransactionAmount());
		transactionForPersistance.setExecutionDate(transfer.getExecutionDate());
		transactionForPersistance.setTransferReason(transfer.getTransferReason());
		Currency currency= currencyService.retrieveCurrencyByName(transfer.getTransactionCurrency());
		transactionForPersistance.setTransactionCurrency(currency);
		transactionForPersistance.setTypeTransferSource("intra en agence");
		transactionForPersistance.setState(stateService.retrieveStateByLibelle("1000"));
		commissionService.persist(commission);
		transactionForPersistance.setCommission(commission);
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
		
				
		TransferType transferType=tarificationService.verifyTransferType(debitAccountNumber, creditAccountNumber);
		Commission commission =tarificationService.retrieveTarification(transferType);
		
		double amount=transfer.getTransactionAmount();
		double comm=amount*commission.getCommissionRate();
		double tva=comm*commission.getTvaRate();
		double sumAmount=amount+comm+tva;
		if(accountService.retrieveBalanceByAccountNumber(debitAccountNumber)>=sumAmount) {
			createTransaction(transfer,commission,transferType);
			accountService.addObligation(debitAccountNumber, sumAmount);
			return "OK";
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
	public void executeTransaction(Transaction transaction) {
		double amount=transaction.getAmount();
		double commission=amount*transaction.getCommission().getCommissionRate();
		double tva=commission*transaction.getCommission().getTvaRate();
		double sumAmount=amount+commission+tva;
		accountService.debitAccount(transaction.getDebitAccount().getAccountNumber(),sumAmount);
		accountService.removeObligation(transaction.getDebitAccount().getAccountNumber(), sumAmount);
		
		String instituteReferenceForDebitAccount=accountService.retrieveAccountResidenceReference(transaction.getDebitAccount().getAccountNumber());
		accountService.creditAccount(accountService.retrieveInstituteAccountNumberPLByReferenceOfInstitut(instituteReferenceForDebitAccount),commission);
		accountService.creditAccount(accountService.retrieveInstituteAccountNumberTVAByReferenceOfInstitut(instituteReferenceForDebitAccount),tva);
		if(transaction.getReference().startsWith("3", 3)) {
			
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
		
		accountService.creditAccount(transaction.getCreditAccount().getAccountNumber(), amount);
		 updateTransactionState(transaction.getReference(), "6000");
		
	}
	
	@Override
	public void updateTransactionState(String transactionRef, String codeState) {
		
		transferDao.updateTransactionState(transactionRef, codeState);
		
	}
	@Override
	public List<Transaction> retrieveTransactionsToExecuteToday(LocalDate date){
		return transferDao.transactionToExecuteToday(date);
		
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

	/*@Override
	public List<Transaction> retrieveTransactionDoByTeller(long TellerRegistrationNumber) {
		// TODO Auto-generated method stub
		return null;
	}*/
	@Override
	public List<Transaction>/*PagedResponse<Transaction>*/ getUserTransfers(String currentUser, int page, int size) {
		//validatePageNumberAndSize(page, size);
		return transferDao.findByCreatedBy(currentUser);
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
	public void doTransactionOfExecutionDayToday() {
		// TODO Auto-generated method stub
		
	}
}
