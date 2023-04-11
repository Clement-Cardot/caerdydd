package com.caerdydd.taf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.caerdydd.taf.models.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

    @Query("SELECT u FROM UserEntity u WHERE u.login = ?1")
    public Optional<UserEntity> findByLogin(String login);
}
