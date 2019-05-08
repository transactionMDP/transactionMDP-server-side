package com.bcp.mdp.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcp.mdp.model.Commission;

public interface CommissionDao extends JpaRepository<Commission, Long> {

	//public Commission saveAndFlush(Commission commission);
	
/*
	public void create(	double commissionRate,double tvaRate);
	public void create(	double commissionRate,double tvaRate,double transferCurrencyToMAD,double transferCurrencyToOther);*/
}
