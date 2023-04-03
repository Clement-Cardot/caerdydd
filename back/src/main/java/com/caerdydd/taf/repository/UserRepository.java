package com.caerdydd.taf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    public UserEntity findByLogin(String login);
}
