package com.bcp.mdp.dao;

import com.bcp.mdp.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyDao extends JpaRepository<Currency, Long> {
	
	Currency findByCode(String code);
}
