package com.bcp.mdp.service;

import com.bcp.mdp.dto.TarificationOfTransaction;
import com.bcp.mdp.model.Commission;
import com.bcp.mdp.model.TransferType;

public interface ITarificationService {
	public TransferType verifyTransferType(long accountDebit, long accountCredit) ;
	//public TarificationOfTransaction retrieveTarification(TransferType transferType, double amount);
	TarificationOfTransaction retrieveTarification(long accountDebit, long accountCredit, double amount);
	Commission retrieveTarification(TransferType transferType);
	Commission tarificationAndValorisation(long accountDebit, long accountCredit);
}
