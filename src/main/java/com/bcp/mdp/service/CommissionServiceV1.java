package com.bcp.mdp.service;

import com.bcp.mdp.dao.CommissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcp.mdp.model.Commission;

@Component("commissionService")
public class CommissionServiceV1 implements ICommissionService {
	
	@Autowired
	private CommissionDao commissionDao;

	@Override
	public void persist(Commission commission) {
		// TODO Auto-generated method stub

		commissionDao.save(commission);
	}

}