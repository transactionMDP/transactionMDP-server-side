package com.bcp.mdp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcp.mdp.model.TransferSource;

public interface TransferSourceDao extends JpaRepository<TransferSource,Long>{
	

	TransferSource findByCode(String code);
}
