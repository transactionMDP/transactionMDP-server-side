package com.bcp.mdp.dao;

import com.bcp.mdp.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDao extends JpaRepository<State, String> {

	public State findByCode(String code);
	


}
