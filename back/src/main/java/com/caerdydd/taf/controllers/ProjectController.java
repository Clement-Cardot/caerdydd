package com.caerdydd.taf.controllers;

import com.caerdydd.taf.exceptions.ResourceNotFoundException;
import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ProjectService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LogManager.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        logger.info("Process request : Get all projects");
        try {
            List<ProjectDTO> projects = projectService.listAllProjects();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error("Unexpected Exception : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
