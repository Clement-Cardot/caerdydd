package com.caerdydd.taf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caerdydd.taf.models.entities.project.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer>{
    @Query("SELECT t FROM TeamEntity t WHERE t.projectDev.id = :projectDevId")
    Optional<TeamEntity> findByProjectDevId(@Param("projectDevId") Integer projectDevId);

    

}
