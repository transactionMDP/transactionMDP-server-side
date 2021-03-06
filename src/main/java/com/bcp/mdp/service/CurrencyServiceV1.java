package com.bcp.mdp.service;

import com.bcp.mdp.dao.CurrencyDao;
import com.bcp.mdp.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CurrencyService")
public class CurrencyServiceV1 implements ICurrencyService {
	
	@Autowired
	private CurrencyDao currencyDao;

	@Override
	public Currency retrieveCurrencyByCode(String code) {
		return currencyDao.findByCode(code);
	}

}
