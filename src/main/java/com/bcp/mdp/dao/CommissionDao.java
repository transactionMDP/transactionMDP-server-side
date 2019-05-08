package com.bcp.mdp.dao;

import com.bcp.mdp.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionDao extends JpaRepository<Commission, Long> {

	//public void create(double commissionRate, double tvaRate);
	//public void create(double commissionRate, double tvaRate, double transferCurrencyToMAD, double transferCurrencyToOther);
}
