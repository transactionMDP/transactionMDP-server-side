package com.bcp.mdp.dao;

import com.bcp.mdp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerDao extends JpaRepository<Customer, Long> {

}
