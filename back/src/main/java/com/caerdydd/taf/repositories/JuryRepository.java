package com.caerdydd.taf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.JuryEntity;
import com.caerdydd.taf.models.entities.UserEntity;

public interface JuryRepository extends JpaRepository<JuryEntity, Integer>{
    Optional<JuryEntity> findByTs1AndTs2(UserEntity ts1, UserEntity ts2);  
    Optional<JuryEntity> findById(int id);  
}
