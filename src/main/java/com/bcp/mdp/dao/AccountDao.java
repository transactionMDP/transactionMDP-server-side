package com.bcp.mdp.dao;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.AccountCategory;
import com.bcp.mdp.model.Customer;
import com.bcp.mdp.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
	
	public List<Account> findByAccountCustomer(Long customerId);
	//public Account findCustomerAccountInfo(/*Long customerId, */ Long accountId);
	public List<Account> findByAccountCategory(Long accountCategoryId);
	@Query("select a.accountCategory from Account a")
	public List<AccountCategory> findAccountCategory();

	@Query("select a.accountCustomer.customerCategory.type from Account a where a.accountNumber=:number")
	public String findCustomerCategoryByAccountId(@Param("number") Long number);
	
	@Query("select a.accountResident from Account a")
	public List<Account> findAccountResident(Long residenceId);
	
	@Query("select distinct a.accountNumber from Account a where a.instituteAccount<>null  " )
	public List<Long> findInstituteAccounts();
	
	@Query(value="select distinct a.accountNumber from Account  a where  a.instituteAccount.instituteReference = :instituteAccount")
	public List<Long> findInstituteAccountsByInstituteReference(@Param("instituteAccount") String instituteAccount);
	
	@Query(value="select distinct a.accountNumber from Account  a"
			+ " where a.instituteAccount.instituteReference = :instituteAccount"
			+ " and a.accountCategory.type='compte de charges et profit'")
	public Long findInstituteAccountNumberPLByInstituteReference(@Param("instituteAccount") String instituteAccount);
	
	@Query(value="select distinct a.accountNumber from Account  a"
			+ " where a.instituteAccount.instituteReference = :instituteAccount"
			+ " and a.accountCategory.type='compte tva'")
	public Long findInstituteAccountNumberTVAByInstituteReference(@Param("instituteAccount") String instituteAccount);
	
	@Query("select  distinct a.accountNumber from Account a where a.accountCategory.type ='compte de charges et profit'")
	public List<Long> findInstituteAccountNumberPL();
	
	@Query("select  distinct a.accountNumber from Account a  where a.accountCategory.type='compte tva' ")
	public List<Long> findInstituteAccountNumberTVA();
	
	@Query("select distinct a.accountNumber from Account a "
			+ " where a.accountCategory.type='compte de liaiqon bpr agence' "
			+ "  and a.accountResident.instituteReference ="
			+ " (select distinct bpr.instituteReference from BankRegional bpr "
			+ " inner join bpr. agencies agency on agency.instituteReference= :refAgence)"
			 )
	public Long findBprLinkAccount(@Param("refAgence") String refAgence);
	

	
	//public Account findByAccountNumber(double accountNumber);
	public Account findByAccountNumber(long accountNumber);
	@Transactional
	@Modifying
	@Query("update Account a set a.balance=a.balance +:amount where a.accountNumber= :number")
	void updateBalanceForAccountNumber(@Param("number") long number, @Param("amount") double amount);
	
	@Transactional
	@Modifying
	@Query("update Account a set a.obligation = a.obligation +:amount where a.accountNumber= :number")
	void updateObligationForAccountNumber(@Param("number") long number, @Param("amount") double amount);

	@Query("Select a.obligation From Account a Where a.accountNumber= :number")
	double findObligationForAccountNumber(@Param("number") long number);
	
	@Query("Select a.balance From Account a Where a.accountNumber= :number")
	double findBalanceForAccountNumber(@Param("number") long number);
	
	@Query("Select a.freeBalance From Account a Where a.accountNumber= ?1")
	double findFreeBalanceForAccountNumber(long accountNumber);
	
	@Query("Select a From Account a Where a.accountNumber= :number")
    Account findAccountByAccountNumber(@Param("number") long number);
	
	@Query("Select a.accountCategory From Account a where  a.accountNumber= :AccountNumber")
	public String findCategoryForAccountNumber(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select distinct state From Account a "
			+ " inner join a.state state on  a.accountNumber= :AccountNumber")
	public State findStateForAccountNumber(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select a.accountCustomer.state From Account a where  a.accountNumber= :AccountNumber")
	public State findStateForAccountCustomer(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select a.accountCustomer.email From Account a where  a.accountNumber= :AccountNumber")
	public String findAccountCustomerEmail(@Param("AccountNumber") long AccountNumber);


	@Query("Select  a.accountCustomer.name From Account a where  a.accountNumber= :AccountNumber")
	public String findAccountCustomerName(@Param("AccountNumber") long AccountNumber);
	

	@Query("Select a.accountCurrency.code From Account a where a.accountNumber= :AccountNumber")
	public String findCurrencyForAccountNumber(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select a.accountResident.instituteReference From Account a where a.accountNumber= :AccountNumber")
	public String findResidenceForAccountNumber(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select a.accountCustomer From Account a where  a.accountNumber= :AccountNumber")
	public Customer findAccountCustomer(@Param("AccountNumber") long AccountNumber);
	
}
