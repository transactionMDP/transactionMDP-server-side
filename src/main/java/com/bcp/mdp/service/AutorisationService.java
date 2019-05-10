package com.bcp.mdp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcp.mdp.dao.AutorisationRepository;
import com.bcp.mdp.model.Autorisation;

@Service
public class AutorisationService implements IAutorisationService {
	
	@Autowired
	private AutorisationRepository autorisationRepository;

	@Override
	public Autorisation check(long autorisationNumber, LocalDate autorisationValidate, long accountNumber) {
		// TODO Auto-generated method stub
		return autorisationRepository.find(autorisationNumber, autorisationValidate, accountNumber);
		//return autorisationRepository.find(autorisationNumber, accountNumber);
		//return  autorisationRepository.findByReferenceAndFinalDate(autorisationNumber, autorisationValidate);
		//return autorisationRepository.findByReference(autorisationNumber);
		//return autorisationRepository.findByFinalDate(autorisationValidate);
		//return autorisationRepository.findByAccountNumber(accountNumber);
	}

	@Override
	public double getBalance(long autorisationNumber) {
		// TODO Auto-generated method stub
		return autorisationRepository.findBalance(autorisationNumber);
	}

	@Override
	public void updateBalance(long autorisationNumber,double amount) {
		// TODO Auto-generated method stub
		autorisationRepository.updateBalance(autorisationNumber, amount);
		
	}

	@Override
	public double getFreeBalance(long autorisationNumber) {
		// TODO Auto-generated method stub
		return autorisationRepository.findFreeBalance(autorisationNumber);
		
	}

	@Override
	public void updateObligation(long autorisationNumber,double amount) {
		// TODO Auto-generated method stub
		 autorisationRepository.updateAmountOfPendingTransfer(autorisationNumber, amount);
	}

}
