package com.bcp.mdp.service;

import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.security.UserPrincipal;

import java.util.Date;
import java.util.List;

public interface ITransferInBPService {

	/*cette fonction permet de stocker en mémoire un virement*/
	/* cette fonction permet d'annuler une transaction , cette annulation peut etre fait par un agent controller
	 * ou bien un agent du ctn
	 */

	
	/* cette fonction permet de tarrifier une transaction en fonction du type de transfert*/
	public void tariffyTransfer();
	public void accountTransfer();
	
	/* cette fonction permet de retourner un transfert qui sera visualisé par un agent controller par exemple*/
	public Transaction checkTransfer(long transactionId);
	
	public Transaction retrieveTransfer(long transactionId);
	public List<Transaction> retrieveTransfers();
	
	/*cette fonction permet de retourner toute les transactions effectuée par l'utilisateur courrant*/
	//public List<Transaction> retrieveTransactionDoByTeller(long TellerRegistrationNumber);
	public List<Transaction>/*PagedResponse<Transaction>*/ getUserTransfers(String currentUser, int page, int size);
	
	/*cette fonction permet de retourner toute les transactions effectuée une agence  donnée*/
	public List<Transaction> retrieveTransactionDoInInstitute(String instituteReference);
	public List<Transaction> retrieveTransactionDoAtOperationDate(Date date);
	public List<Transaction> retrieveTransactionDoAtOperationDateOnAccount(long accountId, Date date);
	/* pourquoi ne pas l'implementer ? p etre pour des raisons comptables
	public void updateTransfer();
	*/
	void doTransfer(TransferDto transfer);

	void createTransaction(TransferDto transfer, long debit, long credit);

	void updateTransactionState(String transactionRef, String codeState);

	void doTransactionOfExecutionDayToday();

//	List<Transaction> retrieveTransactionsToExecuteToday();

	void doTransactionOfExecutionDayToday(List<Transaction> transactions);

	void executeTransaction(Transaction transaction);
	Transaction retrieveByReference(String reference);
}
