package com.caerdydd.taf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.project.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer>{
}
