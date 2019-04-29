package com.bcp.mdp.service;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.AccountCategory;
import com.bcp.mdp.model.State;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
	/* renvoie la liste de tous les comptes */
	public List<Account> retrieveAllAccount();
	public Optional<Account> retrieveAccountDetail(long accountID);

	public void retrieveCustomerAccountInfo();
	public List<AccountCategory> retrieveAccountCategories();

	void createAccount(Account account);
	void updateAccount(Account oldAccount, Account newAccount);
	
	public void verifyResidencesForAccounts(long accountNumberDebiit, long accountNumberCredit);

	void deleteAccount(Account account);
	void deleteAccountById(long id);
	//Account retrieveAccount(long accountNumber);
	List<Account> retrieveCustomerAccounts(long customerId);
	List<Account> retrieveInstituteAccounts(long instituteId);
	List<Account> retrieveACategoryAccounts(long accountCategoryId);
	void creditAccount(long accountNumber, double amount);
	void debitAccount(long accountNumber, double amount);
	Account retrieveAccountByAccountNumber(long accountNumber);
	double retrieveBalanceByAccountNumber(long accountNumber);
	public boolean verifyExistAccount(long accountNumber);
	public String retrieveAccountResidenceReference(long accountNumber);
	
	public String retrieveAccountCurrency(long accountNumber);
	State retrieveAccountState(long accountNumber);
	State retrieveAccountStateOrCurrency(long accountNumber);
	List<Long> retrieveInstituteAccountsPL();
	List<Long> retrieveInstituteAccounts();
	List<Long> retrieveInstituteAccountsTVA();
	List<Long> retrieveInstituteAccountsByReferenceOfInstitut(String reference);
	Long retrieveInstituteAccountNumberPLByReferenceOfInstitut(String reference);
	Long retrieveInstituteAccountNumberTVAByReferenceOfInstitut(String reference);
	Long retrieveBprLinkAccount(String refAgence);
	double retrieveFreeBalanceByAccountNumber(long accountNumber);
	void removeObligation(long debitaccountNumber, double amount);
	void addObligation(long debitaccountNumber, double amount);
<<<<<<< HEAD
	String retrieveAccountCustomerEmail(long accountNumber);
	String retrieveAccountCustomerName(long accountNumber);
	
=======
>>>>>>> 6dee1705fdd3168924339d43b4034cf98f25300c

	String getCustomerCategoryByAccountId(Long idAccount);
}
