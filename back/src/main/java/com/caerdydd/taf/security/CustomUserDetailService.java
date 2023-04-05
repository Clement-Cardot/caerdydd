package com.caerdydd.taf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.services.UserService;

public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        final UserDTO user = userService.getUserByLogin(login);
        return User.withUsername(login)
                    .password(user.getPassword())
                    .authorities(user.getRoleEntities().stream().map(r -> r.getRole()).toArray(String[]::new))
                    .build();
    }
    
}
