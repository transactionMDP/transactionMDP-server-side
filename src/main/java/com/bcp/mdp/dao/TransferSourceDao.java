package com.bcp.mdp.dao;

import com.bcp.mdp.model.TransferSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferSourceDao extends JpaRepository<TransferSource,Long>{
	

	TransferSource findByCode(String code);
}
