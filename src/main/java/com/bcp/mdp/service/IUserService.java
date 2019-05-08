package com.bcp.mdp.service;

import com.bcp.mdp.dao.UserDao;
import com.bcp.mdp.exception.ResourceNotFoundException;
import com.bcp.mdp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserService implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean findUserByRegistrationNumber(String registrationNumber) {
        return userDao.existsByRegistrationNumber(registrationNumber);
    }

    @Override
    public boolean findUserByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User getUserByRegistrationNumber(String registrationNumber) {
        User user = userDao.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User", "registrationNumber", registrationNumber));

        user.setRole(user.getRoles().iterator().next().getName().toString());

        return user;
    }
    /*@Override
        public User getUserByUsername(String username) {
            User user = userDao.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

            user.setRole(user.getRoles().iterator().next().getName().toString());

            return user;
        }*/

    @Override
    public User updateUser(String registrationNumber, User newUser) {
        User user = userDao.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User", "registrationNumber", registrationNumber));

        user.setRole(user.getRoles().iterator().next().getName().toString());
        user.setName(newUser.getName());
        userDao.save(user);
        return user;
    }

}
