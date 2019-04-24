package com.bcp.mdp.service;

import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("transferTypeService")
public class TransferTypeServiceV1 implements ItransferTypeService {
	
	@Autowired
	private TransferTypeDao transferTypeDao;

	@Override
	public double retrieveCommissionValueForTransferType(TransferType transfeType) {
		// TODO Auto-generated method stub
		return transferTypeDao.findTransferTypeCommission(transfeType);
	}
	
	@Override
	public double retrieveTvaValueForTransferType(TransferType transfeType) {
		// TODO Auto-generated method stub
		return transferTypeDao.findTransferTypeTVA(transfeType);
	}


}
