package com.bcp.mdp.service;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Institute;

import java.util.List;

public interface IInstitute {
	public List<Account> retrieveInstituteAccountsOfGestion(String ref);
	
	public void createInstitute();
	public void updateInstitute();
	public void deleteInstitute();
	
	public List<Institute> retrieveInstitutes();
	public void retrieveAgency();
	public void retrieveAgencies();
	public void retrieveAgencyTellers();
	public void retrieveAgenciesTellers();
	public void retrieveRegionalBank();
	public void retrieveRegionalBanks();
	public void retrieveCentralBank();

}
