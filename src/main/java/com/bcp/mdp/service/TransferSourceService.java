package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransferSourceDao;
import com.bcp.mdp.model.TransferSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferSourceService implements ITransferSourceService {

	@Autowired
	private TransferSourceDao transferSourceDao;
	@Override
	public TransferSource retrieveByCode(String code) {
		// TODO Auto-generated method stub
		return transferSourceDao.findByCode(code);
	}

	
}
