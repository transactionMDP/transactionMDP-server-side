package com.bcp.mdp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcp.mdp.model.Commission;

public interface ICommissionService {
	
	public void persist(Commission commission);

}
