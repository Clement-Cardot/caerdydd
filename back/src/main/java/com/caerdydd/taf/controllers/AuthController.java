package com.caerdydd.taf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.repositories.RoleRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.jwt.JwtUtils;
import com.caerdydd.taf.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<UserDTO> login(@RequestBody UserDTO requestUser) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(requestUser.getLogin(), requestUser.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    
    try {
      UserDTO response = userService.getUserByLogin(userDetails.getUsername());
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.SET_COOKIE, jwtCookie.toString());

      return new ResponseEntity<>(
        response,
        headers,
        HttpStatus.OK
      );
    } catch (CustomRuntimeException e) {
      if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("You've been signed out!");
  }
}
