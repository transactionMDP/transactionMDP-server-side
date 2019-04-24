package com.bcp.mdp.dao;

import com.bcp.mdp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    //Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByRegistrationNumberOrEmail(String registrationNumber, String email);

    List<User> findByIdIn(List<Long> userIds);

    //Optional<User> findByUsername(String username);

    Optional<User> findByRegistrationNumber(String registrationNumber);

    //Boolean existsByUsername(String username);

    Boolean existsByRegistrationNumber(String registrationNumber);

    Boolean existsByEmail(String email);
}
