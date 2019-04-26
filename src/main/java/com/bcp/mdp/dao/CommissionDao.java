package com.bcp.mdp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcp.mdp.model.Commission;

public interface CommissionDao extends JpaRepository<Commission, Long> {

}
