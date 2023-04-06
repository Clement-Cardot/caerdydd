package com.caerdydd.taf.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.caerdydd.taf.models.entities.RoleEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final Optional<UserEntity> user = userRepository.findByLogin(login);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User '" + login + "' not found");
        }
        UserEntity userEntity = user.get();
        return User.withUsername(userEntity.getLogin())
                                        .password(userEntity.getPassword())
                                        .authorities(userEntity.getRoleEntities().stream().map(RoleEntity::getRole).toArray(String[]::new))
                                        .build();
    }
    
}
