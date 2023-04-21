package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.ConsultingEntity;

public interface ConsultingRepository extends JpaRepository<ConsultingEntity, Integer> {
}