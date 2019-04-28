package com.bcp.mdp.dao;

import com.bcp.mdp.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrencyDao extends JpaRepository<Currency, Long> {
	
	/*@Query("select c from Currency c where c.name= ?1")
	public Currency retrieveByName(String name);*/
	Currency findByCode(String code);
}
