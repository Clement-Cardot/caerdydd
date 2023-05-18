package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;

public interface TeachingStaffRepository extends JpaRepository<TeachingStaffEntity, Integer>{
}
