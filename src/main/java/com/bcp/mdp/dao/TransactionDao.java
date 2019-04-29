package com.bcp.mdp.dao;

import com.bcp.mdp.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component("transferDao")
public interface TransactionDao extends JpaRepository<Transaction, Long> {
	
	@Transactional
	@Modifying
	@Query("update Transaction t set t.state="
			+ " (select state from State state where state.code=?2)"
			+ " where concat('R',t.idTransaction)=?1")
	public void updateTransactionState(String transactionRef, String codeState);
	
	@Query("select distinct transfer from Transaction transfer "
			+ "where transfer.executionDate= ?1 and "
			+ "transfer.state=(select s from State s where s.code ='3000')"
			+ " ")
	public List<Transaction> transactionToExecuteToday(LocalDate date);

	@Query("select distinct transfer from Transaction transfer where concat('R',transfer.idTransaction)=?1")
	public Transaction findByReference(String reference);

	List<Transaction>findByCreatedBy(String userRegistrationNumber);

	@Query("SELECT t FROM Transaction t where t.state.code = :stateCode")
	List<Transaction> findByState(@Param("stateCode") String stateCode);
}
