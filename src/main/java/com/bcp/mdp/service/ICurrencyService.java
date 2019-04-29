package com.bcp.mdp.service;

import com.bcp.mdp.model.Currency;

public interface ICurrencyService {
	
	public Currency retrieveCurrencyByCode(String code);

}
