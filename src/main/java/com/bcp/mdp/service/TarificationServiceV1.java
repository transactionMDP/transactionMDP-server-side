package com.bcp.mdp.service;

import com.bcp.mdp.dao.AgencyDao;
import com.bcp.mdp.dao.TransactionDao;
import com.bcp.mdp.dao.TransferTypeDao;
import com.bcp.mdp.dto.TarificationOfTransaction;
import com.bcp.mdp.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="tarificationService")
public class TarificationServiceV1 implements ITarificationService {

	@Autowired
	private TransactionDao transferDao;
	
	@Autowired 
	private TransferTypeDao transferTypeDao;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private AgencyDao agencyDao;
	
	@Autowired
	private TransferTypeServiceV1 transferTypeService;


	@Override
	public TransferType verifyTransferType(long accountDebit, long accountCredit) {
		// TODO Auto-generated method stub
	
		String agencyDebit=accountService.retrieveAccountResidenceReference(accountDebit);
		String agencyCredit=accountService.retrieveAccountResidenceReference(accountCredit);

		if
		(agencyDebit.equals(agencyCredit))
		{
			return transferTypeDao.findByType("IntraAgence");
		}
		else 
		{
			if
			( agencyDao.findBankRegionalForAgencyReference(agencyDebit).
					equals( agencyDao.findBankRegionalForAgencyReference(agencyCredit))) 
			{
				return transferTypeDao.findByType("IntraBpr") ;
						
			}
				
				return transferTypeDao.findByType("InterBpr");
		}
	}

	@Override
	public TarificationOfTransaction retrieveTarification(TransferType transferType, double amount) {
		// TODO Auto-generated method stub
		System.out.println(transferType);
		double commissionRate=transferTypeService.retrieveCommissionValueForTransferType(transferType);
		double commissionValue=commissionRate*amount;
		
		double tvaRate=transferTypeService.retrieveTvaValueForTransferType(transferType);
		double tvaValue= tvaRate*commissionValue;// tva sur commission;
		
		double sumTarification= commissionValue+tvaValue;
		
		TarificationOfTransaction tarif=new TarificationOfTransaction() ;
		tarif.setCommissionRate(commissionRate);
		tarif.setCommissionAmount(commissionValue);
		tarif.setTvaRate(tvaRate);
		tarif.setTvaAmount(tvaValue);
		tarif.setSumAmount(sumTarification);
		return tarif;
	}
	
	@Override
	public TarificationOfTransaction retrieveTarification(long accountDebit, long accountCredit, double amount) {
		TransferType transferType=verifyTransferType( accountDebit, accountCredit);
		double commissionRate=transferTypeService.retrieveCommissionValueForTransferType(transferType);
		double commissionValue=commissionRate*amount;
		
		double tvaRate=transferTypeService.retrieveTvaValueForTransferType(transferType);
		double tvaValue= tvaRate*commissionValue;// tva sur commission;
		
		double sumTarification= commissionValue+tvaValue;
		
		TarificationOfTransaction tarif=new TarificationOfTransaction() ;
		tarif.setCommissionRate(commissionRate);
		tarif.setCommissionAmount(commissionValue);
		tarif.setTvaRate(tvaRate);
		tarif.setTvaAmount(tvaValue);
		tarif.setSumAmount(sumTarification);
		return tarif;
	}

}
