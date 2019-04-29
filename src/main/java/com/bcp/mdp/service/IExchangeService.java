package com.bcp.mdp.service;

public interface IExchangeService {
	
	public void createExchange();
	public void updateExchange();
	public void deleteExchange();
	
	public void retrieveExchangeRate();
	public void retrieveExchangesCurrency();
	public void valorizeCurrency();
	public double convertCurrencyAmount(String from, String to, double amount);

	public double valorisation(long accountDebit, double amount, String from, String to);

}
