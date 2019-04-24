package com.bcp.mdp.dao;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface InstituteDao extends JpaRepository<Institute, Long> {
	public Institute findByInstituteReference(String ref);
	
	@Query("select i.accountsOfGestion from Institute i where i.instituteReference= :ref")
	public List<Account> findInstituteAccountsOfGestion(@Param("ref") String ref);
	

}
