package com.bcp.mdp.dao;

import com.bcp.mdp.model.Agency;
import com.bcp.mdp.model.BankRegional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgencyDao extends JpaRepository<Agency, Long> {
	@Query("select a.bankRegional from Agency a where a.instituteReference= :ref")
	public BankRegional findBankRegionalForAgencyReference(@Param("ref") String ref);

}
