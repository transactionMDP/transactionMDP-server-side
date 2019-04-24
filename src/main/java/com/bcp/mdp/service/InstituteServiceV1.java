package com.bcp.mdp.service;

import com.bcp.mdp.dao.InstituteDao;
import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Institute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("instituteService")
public class InstituteServiceV1 implements IInstitute {
	@Autowired 
	private InstituteDao instituteDao;

	@Override
	public List<Account> retrieveInstituteAccountsOfGestion(String ref) {
		// TODO Auto-generated method stub
		return instituteDao.findInstituteAccountsOfGestion(ref);
	}

	@Override
	public void createInstitute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInstitute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteInstitute() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Institute> retrieveInstitutes() {
		// TODO Auto-generated method stub
		return instituteDao.findAll();

	}

	@Override
	public void retrieveAgency() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAgencies() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAgencyTellers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAgenciesTellers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveRegionalBank() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveRegionalBanks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveCentralBank() {
		// TODO Auto-generated method stub

	}

}
