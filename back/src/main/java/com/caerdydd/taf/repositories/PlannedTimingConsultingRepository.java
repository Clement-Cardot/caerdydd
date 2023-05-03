package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.PlannedTimingConsultingEntity;

public interface PlannedTimingConsultingRepository extends JpaRepository<PlannedTimingConsultingEntity, Integer> {
}