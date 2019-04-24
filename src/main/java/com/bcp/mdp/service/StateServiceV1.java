package com.bcp.mdp.service;

import com.bcp.mdp.dao.StateDao;
import com.bcp.mdp.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("stateService")
public class StateServiceV1 implements IStateService {
	@Autowired
	private StateDao stateDao;
	
	@Override
	public State retrieveStateByLibelle(String code) {
		return stateDao.findByCode(code);
	}
	

}
