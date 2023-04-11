package com.caerdydd.taf.services;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class LoginService {

    private static final Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    public UserDTO login(String login, String password) throws CustomRuntimeException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
        logger.info("Auth Token Generated for login : {}, token {}", login, token);
        try {
            this.authenticationManager.authenticate(token);
            logger.info("Auth Status : {}", token);
            
            return userService.getUserByLogin(login);
        } catch (BadCredentialsException e) {
            logger.info("Authentication Failed for login : {}, BadCredentialesException occur !", login);
            return null;
        } 
    }
    
}
