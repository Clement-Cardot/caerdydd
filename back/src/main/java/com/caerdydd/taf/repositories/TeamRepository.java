package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer>{

}
