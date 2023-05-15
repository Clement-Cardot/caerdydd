package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.consulting.PlannedTimingAvailabilityEntity;

public interface PlannedTimingAvailabilityRepository extends JpaRepository<PlannedTimingAvailabilityEntity, Integer>{
}