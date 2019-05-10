package com.bcp.mdp.dao;

import com.bcp.mdp.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionDao extends JpaRepository<Commission, Long> {

}
