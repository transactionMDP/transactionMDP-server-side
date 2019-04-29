package com.bcp.mdp.service;

import com.bcp.mdp.model.TransferSource;

public interface ITransferSourceService {
	
	public TransferSource retrieveByCode(String code);

}
