package com.caerdydd.taf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;

public interface JuryRepository extends JpaRepository<JuryEntity, Integer>{
    Optional<JuryEntity> findByTs1AndTs2(TeachingStaffEntity ts1, TeachingStaffEntity ts2);
    Optional<JuryEntity> findByTs1(TeachingStaffEntity ts1);
    Optional<JuryEntity> findByTs2(TeachingStaffEntity ts2);
    Optional<JuryEntity> findById(int id);  
}
