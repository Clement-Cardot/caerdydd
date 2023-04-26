package com.caerdydd.taf.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer projectId, @RequestBody ProjectDTO projectDTO) throws CustomRuntimeException {
        projectDTO.setIdProject(projectId);
        ProjectDTO updatedProject = projectService.updateProject(projectDTO);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
}


    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Integer projectId) throws CustomRuntimeException {
        ProjectDTO project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<String> handleCustomRuntimeException(CustomRuntimeException e) {
        HttpStatus status;
        String message = e.getMessage();

        if (CustomRuntimeException.USER_NOT_FOUND.equals(message) || CustomRuntimeException.TEAM_NOT_FOUND.equals(message) || CustomRuntimeException.PROJECT_NOT_FOUND.equals(message) || CustomRuntimeException.TEAM_MEMBER_NOT_FOUND.equals(message)) {
            status = HttpStatus.NOT_FOUND;
        } else if (CustomRuntimeException.USER_NOT_IN_ASSOCIATED_TEAM.equals(message) || CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER.equals(message)) {
            status = HttpStatus.FORBIDDEN;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(message, status);
    }
}
