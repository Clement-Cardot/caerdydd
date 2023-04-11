package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.services.ProjectService;

@RestController
@RequestMapping("/api/users")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<List<ProjectDTO>> list() {
        try {
            List<ProjectDTO> projects = projectService.listAllProjects();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
