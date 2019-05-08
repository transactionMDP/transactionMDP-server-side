package com.bcp.mdp.service;

import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.model.Commission;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
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

	public List<Transaction>/*PagedResponse<Transaction>*/ getTransfersByState(String stateCode, int page, int size);

	/*cette fonction permet de retourner toute les transactions effectuée une agence  donnée*/
	public List<Transaction> retrieveTransactionDoInInstitute(String instituteReference);
	public List<Transaction> retrieveTransactionDoAtOperationDate(Date date);
	public List<Transaction> retrieveTransactionDoAtOperationDateOnAccount(long accountId, Date date);
	/* pourquoi ne pas l'implementer ? p etre pour des raisons comptables
	public void updateTransfer();
	*/

	String doTransfer(UserPrincipal currentUser, TransferDto transfer);
	//void createTransaction(TransferDto transfer, long debit, long credit);

	//void doTransfer(TransferDto transfer);

	//void createTransaction(TransferDto transfer, long debit, long credit);


	void updateTransactionState(String transactionRef, String codeState);

	//	List<Transaction> retrieveTransactionsToExecuteToday();

	void doTransactionOfExecutionDayToday(List<Transaction> transactions) throws MessagingException, IOException;

	void executeTransaction(Transaction transaction) throws MessagingException, IOException;
	Transaction retrieveByReference(String reference);
	void createIntermediaireTransaction(long debitAccount, long creditAccount, double commission);
	//void createTransaction(TransferDto transfer);
	List<Transaction> retrieveTransactionsToExecuteToday(LocalDate date);
	void createTransaction(TransferDto transfer, Commission commission);


	/*User*/void storeFile(MultipartFile file, String username);
}
