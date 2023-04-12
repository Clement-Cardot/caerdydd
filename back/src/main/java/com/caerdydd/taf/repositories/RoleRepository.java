package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    RoleEntity findByIdRole(Integer idRole);
    RoleEntity deleteByIdRole(Integer idRole);

}
