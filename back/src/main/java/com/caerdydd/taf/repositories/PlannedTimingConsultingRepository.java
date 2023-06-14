package com.caerdydd.taf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.consulting.PlannedTimingConsultingEntity;
import java.time.LocalDateTime;


public interface PlannedTimingConsultingRepository extends JpaRepository<PlannedTimingConsultingEntity, Integer> {
    List<PlannedTimingConsultingEntity> findByDatetimeBegin(LocalDateTime datetimeBegin);
}