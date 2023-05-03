package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.TeachingStaffEntity;

public interface TeachingStaffRepository extends JpaRepository<TeachingStaffEntity, Integer>{
}