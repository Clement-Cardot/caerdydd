package com.caerdydd.taf.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;

public interface JuryRepository extends JpaRepository<JuryEntity, Integer>{
    Optional<JuryEntity> findByTs1AndTs2(TeachingStaffEntity ts1, TeachingStaffEntity ts2);  
    Optional<JuryEntity> findById(int id);
    List<JuryEntity> findAll();
    @Query("SELECT ts FROM TeachingStaffEntity ts WHERE ts.id IN (SELECT j.ts1.id FROM JuryEntity j WHERE j.id = :idJury) OR ts.id IN (SELECT j.ts2.id FROM JuryEntity j WHERE j.id = :idJury)")
    List<TeachingStaffEntity> findTeachingStaffMembers(@Param("idJury") Integer idJury);
    Optional<JuryEntity> findByTs1(TeachingStaffEntity ts1);
    Optional<JuryEntity> findByTs2(TeachingStaffEntity ts2);
}
