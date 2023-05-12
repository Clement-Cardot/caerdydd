package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.PresentationEntity;

public interface PresentationRepository extends JpaRepository<PresentationEntity, Integer> {
    
}
