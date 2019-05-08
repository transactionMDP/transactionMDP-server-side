package com.bcp.mdp.service;

import com.bcp.mdp.model.User;

public interface UserService {
    //public boolean findUserByUsername(String username);

    public boolean findUserByRegistrationNumber(String registrationNumber);

    public boolean findUserByEmail(String email);

    //public User getUserByUsername(String username);

    public User getUserByRegistrationNumber(String registrationNumber);

    public User updateUser(String registrationNumber, User user);
}
