package com.caerdydd.taf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.caerdydd.taf.models.UserEntity;
import com.caerdydd.taf.repository.UserRepository;

public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByLogin(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return User.withUsername(login)
                    .password(userEntity.getPassword())
                    .authorities(userEntity.getRole())
                    .build();
    }
    
}
