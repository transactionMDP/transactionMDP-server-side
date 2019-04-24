package com.bcp.mdp.service;

import com.bcp.mdp.model.TransferType;

public interface ItransferTypeService {
	
	double retrieveCommissionValueForTransferType(TransferType transfeType);

	double retrieveTvaValueForTransferType(TransferType transfeType);

}
