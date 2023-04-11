package com.caerdydd.taf.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.ProjectEntity;

public class ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    
    public Optional<ProjectEntity> findAll();
}
