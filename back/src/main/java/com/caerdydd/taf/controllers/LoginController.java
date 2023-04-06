package com.caerdydd.taf.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.services.LoginService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> processLogin(@RequestBody UserDTO requestUser) {
        logger.info("processLogin() - {}", requestUser.getLogin());
        Boolean result = loginService.login(requestUser.getLogin(), requestUser.getPassword());
        logger.info("processLogin() result : {}", result);
        if (Boolean.TRUE.equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }    
}
