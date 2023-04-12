package com.caerdydd.taf.controllers;

import com.caerdydd.taf.exceptions.ResourceNotFoundException;
import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams/{teamId}/projects")
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer teamId,
                                                     @PathVariable Integer projectId,
                                                     @RequestBody ProjectDTO projectDTO,
                                                     @RequestParam Integer userId) throws ResourceNotFoundException {

        projectDTO.setIdProject(projectId);

        ProjectDTO updatedProjectDTO = projectService.updateProject(teamId, userId, projectDTO);

        return new ResponseEntity<>(updatedProjectDTO, HttpStatus.OK);
    }
}
