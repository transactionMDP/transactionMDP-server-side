package com.bcp.mdp.service;

import com.bcp.mdp.dao.AccountDao;
import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.AccountCategory;
import com.bcp.mdp.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("accountService")
public class AccountServiveV1 implements IAccountService {
	
	@Autowired
	private AccountDao accountdao;

	@Override
	public void createAccount(Account account) {
		// TODO Auto-generated method stub
		accountdao.save(account);
	
		

	}

	@Override
	public void updateAccount(Account oldAccount, Account newAccount) {
		// TODO Auto-generated method stub
		//accountdao.updateAccount(oldAccount, newAccount);

	}

	@Override
	public void deleteAccount(Account account) {
		// TODO Auto-generated method stub
		accountdao.delete(account);

	}
	
	@Override
	public void deleteAccountById(long id) {
		// TODO Auto-generated method stub
		accountdao.deleteById(id);
		
	}

	@Override
	public List<Account> retrieveAllAccount() {
		// TODO Auto-generated method stub
		return accountdao.findAll();

	}

	@Override
	public Account retrieveAccountByAccountNumber(long accountNumber) {
		// TODO Auto-generated method stub
		return accountdao.findAccountByAccountNumber(accountNumber);

	}

	@Override
	public List<Account> retrieveCustomerAccounts(long customerId) {
		// TODO Auto-generated method stub
		return accountdao.findByAccountCustomer(customerId);

	}

	@Override
	public void retrieveCustomerAccountInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Account> retrieveInstituteAccounts(long instituteId) {
		// TODO Auto-generated method stub
		return accountdao.findAccountResident(instituteId);

	}

	@Override
	public List<AccountCategory> retrieveAccountCategories() {
		// TODO Auto-generated method stub
		return accountdao.findAccountCategory();

	}

	@Override
	public List<Account>retrieveACategoryAccounts(long accountCategoryId) {
		// TODO Auto-generated method stub
		return accountdao.findByAccountCategory(accountCategoryId);

	}

	@Override
	public void creditAccount(long accountNumber, double amount) {
		// TODO Auto-generated method stub
		 accountdao.updateBalanceForAccountNumber(accountNumber, amount+accountdao.findBalanceForAccountNumber(accountNumber));
	}

	@Override
	public void debitAccount(long accountNumber, double amount) {
		// TODO Auto-generated method stub
		accountdao.updateBalanceForAccountNumber(accountNumber, accountdao.findBalanceForAccountNumber(accountNumber)-amount);

	}

	@Override
	public double retrieveBalanceByAccountNumber(long accountNumber) {
		// TODO Auto-generated method stub
		return accountdao.findBalanceForAccountNumber(accountNumber);

	}

	@Override
	public boolean verifyExistAccount(long accountNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void verifyResidencesForAccounts(long accountNumberDebiit, long accountNumberCredit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Account> retrieveAccountDetail(long accountId) {
		// TODO Auto-generated method stub
		return accountdao.findById(accountId);
	}

	@Override
	public String retrieveAccountCurrency(long accountNumber) {
		// TODO Auto-generated method stub
		return accountdao.findCurrencyForAccountNumber(accountNumber);
	}
	
	@Override
	public State retrieveAccountState(long accountNumber) {
		return accountdao.findStateForAccountNumber(accountNumber);
	}
	
	@Override
	public State retrieveAccountStateOrCurrency(long accountNumber) {
		State stateAccount=retrieveAccountState(accountNumber);
		if(retrieveAccountByAccountNumber(accountNumber)==null){
			stateAccount=new State();
			stateAccount.setCode("404");
			stateAccount.setLibelle("compte inexistant");
			return stateAccount;
		}
		
		State stateCustomer=accountdao.findStateForAccountCustomer(accountNumber);
		if( stateAccount.getCode().equals("200")&& stateCustomer.getCode().equals("200")){
			stateAccount.setLibelle(accountdao.findCurrencyForAccountNumber(accountNumber));
			
			return stateAccount;
					
		}
		else {
			if(stateAccount.getCode().equals("200")==false) {
				return stateAccount;
			}	
		}
	
		return stateCustomer;
		
	}
	
	@Override
	public String retrieveAccountResidenceReference(long accountNumber) {
		return  accountdao.findResidenceForAccountNumber(accountNumber);
	}

	@Override
	public List<Long> retrieveInstituteAccounts(){
		return accountdao.findInstituteAccounts();
	}

	@Override
	public List<Long> retrieveInstituteAccountsPL() {
		// TODO Auto-generated method stub
		return accountdao.findInstituteAccountNumberPL();
	}
	
	@Override
	public List<Long> retrieveInstituteAccountsTVA() {
		// TODO Auto-generated method stub
		return accountdao.findInstituteAccountNumberTVA();
	}
	
	
	@Override
	public List<Long> retrieveInstituteAccountsByReferenceOfInstitut(String reference) {
		// TODO Auto-generated method stub
		return accountdao.findInstituteAccountsByInstituteReference(reference);
	}


	@Override
	public Long retrieveInstituteAccountNumberPLByReferenceOfInstitut(String reference) {
		// TODO Auto-generated method stub
		return accountdao.findInstituteAccountNumberPLByInstituteReference(reference);
	}

	@Override
	public Long retrieveInstituteAccountNumberTVAByReferenceOfInstitut(String reference) {
		// TODO Auto-generated method stub
		return accountdao.findInstituteAccountNumberTVAByInstituteReference(reference);
	}
	
	@Override
	public Long retrieveBprLinkAccount( String refAgence) {
		return accountdao.findBprLinkAccount(refAgence);
	}

}
