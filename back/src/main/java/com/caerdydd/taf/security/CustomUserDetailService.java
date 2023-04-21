package com.caerdydd.taf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.services.UserService;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService{

    private static final Logger logger = LogManager.getLogger(CustomUserDetailService.class);

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDTO user;
        try {
            user = userService.getUserByLogin(login);
            return User.withUsername(login)
                    .password(user.getPassword())
                    .authorities(user.getRoles().stream().map(RoleDTO::getRole).toArray(String[]::new))
                    .build();
        } catch (CustomRuntimeException e) {
            logger.error(e);
            throw new UsernameNotFoundException("Login not found ! ");
        }
    }
    
}
