package com.bcp.mdp.dao;

import com.bcp.mdp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("transferDao")
public interface TransactionDao extends JpaRepository<Transaction, Long> {

	/*@Transactional
	@Modifying
	@Query("insert into Transaction "
			+ " values(transferDto.debitAccount,transferDto.creditAccount ,transferDto.amount")
	public void createTransaction(TransferDto transferDto);*/
    
	//public List<Transaction> retrieveTransactionDoByTeller();
	
	@Query("select distinct transfer from Transaction transfer where transfer <>null ")
	public List<Transaction> findTransactions();
	
	@Query("select distinct transfer from Transaction transfer where transfer <>null ")
	public List<Transaction> findTransactionDoByTeller();
	
	@Query("update Transaction t set t.state="
			+ " (select state from State state where state.code=?2)"
			+ " where concat('R',t.idTransaction)=?1")
	public void updateTransactionState(String transactionRef, String codeState);
	
	public List<Transaction> findByExecutionDateAndExecuted(Date executionDate, Boolean executed);

	@Query("select distinct transfer from Transaction transfer where concat('R',transfer.idTransaction)=?1")
	public Transaction findByReference(String reference);
}
