package com.bcp.mdp.service;

import com.bcp.mdp.model.Customer;
import com.bcp.mdp.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
	
	public void createCustomer(Customer customer);
	public void updateCustomer(Customer newCustomer, long oldCustomerId);
	public void deleteCustomer(long CustomerId);

	public  Optional<Customer> retrieveCustomerDetail(long CustomerId);
	public List<Transaction>retrieveCustomerTransactions(long CustomerId);
	public List<Customer> retrieveAllCustomer();
	//public void addAccountForCustomer(long CustomerId, Account account);
	
	public void retrieveCustomerState();
	public void retrieveCustomerAuthorization();
	
	


}
