package com.bcp.mdp.service;

import java.time.LocalDate;

import com.bcp.mdp.model.Autorisation;

public interface IAutorisationService {
	
	public Autorisation check(long autorisationNumber, LocalDate  autorisationValidate, long accountNumber);
	
	public double getBalance(long autorisationNumber);
	
	public void updateBalance(long autorisationNumber,double amount);
	
	public double getFreeBalance(long autorisationNumber);

	public void updateObligation(long autorisationNumber,double amount);

}
