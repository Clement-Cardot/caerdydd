package com.caerdydd.taf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    public Optional<UserEntity> findByLogin(String login);
}
