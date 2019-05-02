package com.bcp.mdp.service;

import com.bcp.mdp.dto.ValorisationRequest;
import com.bcp.mdp.dto.ValorisationResponse;

public interface IExchangeService {
	
	public void createExchange();
	public void updateExchange();
	public void deleteExchange();
	
	public void retrieveExchangeRate();
	public void retrieveExchangesCurrency();
	//public void valorizeCurrency();
	//public double convertCurrencyAmount(String from, String to, double amount);

	public double valorisation(long accountDebit, double amount, String from, String to);
	double convertCurrencyAmount(String transactionCurrency, double amount);
	//ValorisationResponse valorise(ValorisationRequest valorisationRequest);
	ValorisationResponse valoriseCommission(ValorisationRequest valorisationRequest);

}
