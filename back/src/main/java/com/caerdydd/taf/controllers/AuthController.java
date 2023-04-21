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
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger logger = LogManager.getLogger(AuthController.class);

  @Autowired
  AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody UserDTO requestUser) {
    logger.info("Process request : Login user : {} password : {}", requestUser.getLogin(), requestUser.getPassword());
    try{
      return authService.loginUser(requestUser.getLogin(), requestUser.getPassword());
    } catch (CustomRuntimeException e) {
      if (e.getMessage().equals(CustomRuntimeException.USER_NOT_FOUND)) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (e.getMessage().equals(CustomRuntimeException.USER_PASSWORD_NOT_MATCH)) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
    
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logoutUser() {
    logger.info("Process request : Logout user");
    try{
      return authService.logoutUser();
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
  }
}
