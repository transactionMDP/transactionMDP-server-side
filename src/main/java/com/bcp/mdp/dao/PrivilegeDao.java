package com.bcp.mdp.dao;

import com.bcp.mdp.model.Privilege;
import com.bcp.mdp.model.PrivilegeName;
import com.bcp.mdp.model.Role;
import com.bcp.mdp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PrivilegeDao extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(PrivilegeName privilegeName);
}
