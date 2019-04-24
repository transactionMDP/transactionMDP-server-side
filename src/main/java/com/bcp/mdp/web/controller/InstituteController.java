package com.bcp.mdp.web.controller;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.Institute;
import com.bcp.mdp.service.InstituteServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instituteV1")
public class InstituteController {
	@Autowired
	private InstituteServiceV1 instituteService;
	
	@PostMapping(value="/instituteAccountsOfGestion")
	public List<Account> retrieveInstituteAccounts(@RequestBody String refInstitute) {
	
		return instituteService.retrieveInstituteAccountsOfGestion(refInstitute);
	
	}
	
	@GetMapping(value="/instituteList")
	public List<Institute> retrieveInstituteList() {
	
		return instituteService.retrieveInstitutes();
	
	}

}
