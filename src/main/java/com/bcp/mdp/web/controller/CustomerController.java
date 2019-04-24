package com.bcp.mdp.web.controller;

import com.bcp.mdp.model.Customer;
import com.bcp.mdp.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	@PostMapping(value="/create")
	public void createCustomer(@RequestBody Customer customer) {
		System.out.println("hoho");
		customerService.createCustomer(customer);
		
	}
	
	@GetMapping(value="/customerDetail/{customerId}")
	public Optional<Customer> retrieveCustomer(@PathVariable  long customerId) {
		return customerService.retrieveCustomerDetail(customerId);
		
	}
	
	@GetMapping(value="/list")
	public List<Customer> retrieveCustomer(){
		return customerService.retrieveAllCustomer();
	}

}
