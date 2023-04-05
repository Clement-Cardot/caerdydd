package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
