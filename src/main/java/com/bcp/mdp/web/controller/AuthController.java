package com.bcp.mdp.web.controller;

import com.bcp.mdp.dao.RoleDao;
import com.bcp.mdp.dao.UserDao;
import com.bcp.mdp.dto.ApiResponse;
import com.bcp.mdp.dto.JwtAuthenticationResponse;
import com.bcp.mdp.exception.AppException;
import com.bcp.mdp.model.Role;
import com.bcp.mdp.model.RoleName;
import com.bcp.mdp.model.User;
import com.bcp.mdp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getRegistrationNumberOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpRequest) {
        if(userDao.existsByRegistrationNumber(signUpRequest.getRegistrationNumber())) {
            return new ResponseEntity(new ApiResponse(false, "RegistrationNumber is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userDao.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getRegistrationNumber(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = new Role();
        User result = new User();

        if(signUpRequest.getRole().equals(RoleName.ROLE_AGENT.toString())){
            userRole = roleDao.findByName(RoleName.ROLE_AGENT)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }
        else if(signUpRequest.getRole().equals(RoleName.ROLE_CTRL.toString())){
            userRole = roleDao.findByName(RoleName.ROLE_CTRL)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }
        else {
            userRole = roleDao.findByName(RoleName.ROLE_CTN)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }

        user.setRoles(Collections.singleton(userRole));
        result = userDao.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getRegistrationNumber()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

}
