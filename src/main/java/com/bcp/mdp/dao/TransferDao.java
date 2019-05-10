package com.bcp.mdp.dao;

import com.bcp.mdp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TransferDao extends JpaRepository<Transaction, Long> {
	@Query("select distinct transaction from Transaction transaction where transaction<>null")
	public  List<Transaction> findAll();
	
	@Transactional
	@Modifying
	@Query("update Transaction t set t.state="
			+ " (select state from State state where state.code=?2)"
			+ " where t.reference=?1")
	public void updateTransactionState(String transactionRef, String codeState);
	
	@Query("select distinct transfer from Transaction transfer "
			+ "where transfer.executionDate= ?1 and "
			+ "transfer.state.code ='3000'"
			+ " ")
	public List<Transaction> transactionToExecuteToday(LocalDate date);

	@Query("select distinct transfer from Transaction transfer where transfer.reference=?1")
	public Transaction findByReference(String reference);

	List<Transaction>findByCreatedBy(String userRegistrationNumber);

	@Query("SELECT t FROM Transaction t where t.state.code = :stateCode")
	List<Transaction> findByState(@Param("stateCode") String stateCode);
}
