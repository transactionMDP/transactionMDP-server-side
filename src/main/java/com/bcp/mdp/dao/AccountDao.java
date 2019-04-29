package com.bcp.mdp.dao;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.AccountCategory;
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
	
	@Query("select distinct a.accountNumber from Account a"
			+ " inner join a.instituteAccount institute on institute<>null  " )
	public List<Long> findInstituteAccounts();
	
	@Query(value="select distinct a.accountNumber from Account  a"
			+ " inner join a.instituteAccount institute on  institute.instituteReference = :instituteAccount")
	public List<Long> findInstituteAccountsByInstituteReference(@Param("instituteAccount") String instituteAccount);
	
	@Query(value="select distinct a.accountNumber from Account  a"
			+ " inner join a.instituteAccount institute on  institute.instituteReference = :instituteAccount"
			+ " inner join a.accountCategory  category on category.type='compte de charges et profit'")
	public Long findInstituteAccountNumberPLByInstituteReference(@Param("instituteAccount") String instituteAccount);
	
	@Query(value="select distinct a.accountNumber from Account  a"
			+ " inner join a.instituteAccount institute on  institute.instituteReference = :instituteAccount"
			+ " inner join a.accountCategory  category on category.type='compte tva'")
	public Long findInstituteAccountNumberTVAByInstituteReference(@Param("instituteAccount") String instituteAccount);
	
	@Query("select  distinct a.accountNumber from Account a  "
			+ " inner join a.accountCategory  category on category.type='compte tva'")
	public List<Long> findInstituteAccountNumberPL();
	
	@Query("select  distinct a.accountNumber from Account a  "
			+ " inner join a.accountCategory  category on category.type='compte tva' ")
	public List<Long> findInstituteAccountNumberTVA();
	
	@Query("select distinct a.accountNumber from Account a "
			+ " inner join a.accountCategory  category on category.type='compte de liaiqon bpr agence' "
			+ "  inner join a.accountResident residence on residence.instituteReference ="
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
	
	@Query("Select customer.state From Account a "
			+ " inner join a.accountCustomer customer "
			+ "where  a.accountNumber= :AccountNumber")
	public State findStateForAccountCustomer(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select customer.email From Account a "
			+ " inner join a.accountCustomer customer "
			+ "where  a.accountNumber= :AccountNumber")
	public String findAccountCustomerEmail(@Param("AccountNumber") long AccountNumber);

<<<<<<< HEAD
	@Query("Select customer.name From Account a "
			+ " inner join a.accountCustomer customer "
			+ "where  a.accountNumber= :AccountNumber")
	public String findAccountCustomerName(@Param("AccountNumber") long AccountNumber);
	@Query("Select currency.name From Account a  "
=======
	@Query("Select currency.code From Account a  "
>>>>>>> 6dee1705fdd3168924339d43b4034cf98f25300c
			+ " inner join a.accountCurrency currency  "
			+ " on  a.accountNumber= :AccountNumber")
	public String findCurrencyForAccountNumber(@Param("AccountNumber") long AccountNumber);
	
	@Query("Select residence.instituteReference From Account a "
			+ " inner join a.accountResident residence "
			+ " on  a.accountNumber= :AccountNumber")
	public String findResidenceForAccountNumber(@Param("AccountNumber") long AccountNumber);
	

	
	
	/*ici on ne va preciser que les fonctions spécifiques à un compte,  le reste des fonctions standarts sont dejà definies 
	 * dans jpaRepository
	 */
	/* la fonction findAccountBalance permet de retourner le solde d'un compte grace au numero de compte*/
	//public double findAccountBalance(Long idAccount);
	
	//la fonction creditAccount permet de crediter un compte en lui  donnant un numero de compte et un montant à crediter
	//public void creditAccount(@Param("accountNumber")long accountNumber, @Param("amount") double amount);
	
	/*la fonction debitAccount permet de debiter un compte en lui  donnant un numero de compte et un montant à debiter*/
	//public void debitAccount(Long accountNumber, double amount);
	
	/*
	@Query("update Account a set "
			+ "a.accountNumber= :newAccount.accountNumber "
			+ "where a.accountNumber= :oldAccount.accountNumber")
	public void updateAccount(Account oldAccount, Account newAccount); */
	//public boolean retrieveExistAccount(long accountNumber);

	/*Account findByAccountNumber(double accountNumber);

	Account findByAccountNumber(Long accountNumber);*/

	
	
	

}
