package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.RoleEntity;

public interface TeachingStaffRepository extends JpaRepository<RoleEntity, Long> {
  
}
