package com.bcp.mdp.security;

import com.bcp.mdp.dao.UserDao;
import com.bcp.mdp.exception.ResourceNotFoundException;
import com.bcp.mdp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String registrationNumberOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either registrationNumber or email
        User user = userDao.findByRegistrationNumberOrEmail(registrationNumberOrEmail, registrationNumberOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with registrationNumber or email : " + registrationNumberOrEmail)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userDao.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
