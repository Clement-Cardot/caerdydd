package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.user.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    RoleEntity findByIdRole(Integer idRole);
    RoleEntity deleteByIdRole(Integer idRole);

}
