package com.caerdydd.taf.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;

public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByLogin(login);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }
        return User.withUsername(login)
                    .password(userEntity.get().getPassword())
                    .authorities(userEntity.get().getRole())
                    .build();
    }
    
}
