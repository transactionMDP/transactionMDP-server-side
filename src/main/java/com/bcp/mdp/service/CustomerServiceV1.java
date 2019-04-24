package com.bcp.mdp.service;

import com.bcp.mdp.dao.CustomerDao;
import com.bcp.mdp.model.Customer;
import com.bcp.mdp.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("customerService")
public class CustomerServiceV1 implements ICustomerService {
	@Autowired
	private CustomerDao customerdao;

	@Override
	public void createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerdao.save(customer);

	}

	@Override
	public void updateCustomer(Customer newCustomer, long oldCustomerId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(long customerId) {
		// TODO Auto-generated method stub
		customerdao.deleteById(customerId);

	}

	@Override
	public Optional<Customer> retrieveCustomerDetail(long customerId) {
		// TODO Auto-generated method stub
		return customerdao.findById(customerId);

	}

	@Override
	public List<Transaction> retrieveCustomerTransactions(long CustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> retrieveAllCustomer() {
		// TODO Auto-generated method stub
		return customerdao.findAll();

	}

	@Override
	public void retrieveCustomerState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveCustomerAuthorization() {
		// TODO Auto-generated method stub

	}

}
