package com.bcp.mdp.service;

public interface IExchange {
	
	public void createExchange();
	public void updateExchange();
	public void deleteExchange();
	
	public void retrieveExchangeRate();
	public void retrieveExchangesCurrency();
	public void valorizeCurrency();

}
