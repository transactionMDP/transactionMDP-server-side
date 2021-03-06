package com.bcp.mdp.dao;

import com.bcp.mdp.model.Role;
import com.bcp.mdp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
