package com.caerdydd.taf.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private static final Logger logger = LogManager.getLogger(AuthController.class);
  private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

  @Autowired
  AuthService authService;

  @GetMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestParam String login, @RequestParam String password) {
    logger.info("Process request : Login user : {} password : {}", login, password);
    try{
      return authService.loginUser(login, password);
    } catch (CustomRuntimeException e) {
      if (e.getMessage().equals(CustomRuntimeException.USER_NOT_FOUND)) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (e.getMessage().equals(CustomRuntimeException.USER_PASSWORD_NOT_MATCH)) {
        logger.warn(e.getMessage());
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }

      logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logoutUser() {
    logger.info("Process request : Logout user");
    try{
      return authService.logoutUser();
    } catch (Exception e) {
      logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
      return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
  }
}
