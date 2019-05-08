package com.bcp.mdp.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.bcp.mdp.service.IExchangeService;

@CrossOrigin
@RestController
@RequestMapping("/api/valorise")
public class ValorisationController {
	 @Autowired
	 private IExchangeService exchangeService; 
	
	@PostMapping
	public double valorise(@RequestParam long accountDebit,@RequestParam double amount,@RequestParam String transactionCurrency,@RequestParam String to)
	{
		return exchangeService.valorisation(accountDebit, amount, transactionCurrency, to);
	}
}
