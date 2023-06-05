package com.caerdydd.taf.controllers;
import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)

public class ProjectControllerTest {

@InjectMocks
private ProjectController projectController;

@Mock
private ProjectService projectService;

    @Test
    void testGetProject_Nominal() throws CustomRuntimeException {
        Integer projectId = 1;
        ProjectDTO project = new ProjectDTO();

        when(projectService.getProjectById(projectId)).thenReturn(project);

        ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(project, HttpStatus.OK);

        ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

        assertEquals(expectedAnswer, result);
        verify(projectService, times(1)).getProjectById(projectId);
    }



@Test
void testGetProject_ProjectNotFound() throws CustomRuntimeException {
    Integer projectId = 1;

    when(projectService.getProjectById(projectId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).getProjectById(projectId);
}

@Test
void testGetProject_ServiceError() throws CustomRuntimeException {
    Integer projectId = 1;

    when(projectService.getProjectById(projectId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).getProjectById(projectId);
}

@Test
void testGetProject_UnexpectedException() throws CustomRuntimeException {
    Integer projectId = 1;

    when(projectService.getProjectById(projectId)).thenThrow(new CustomRuntimeException("Unexpected error"));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).getProjectById(projectId);
}

@Test
void testUpdateDescription_UnexpectedException() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();

    when(projectService.updateDescription(any(ProjectDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    ResponseEntity<ProjectDTO> result = projectController.updateDescription(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateDescription(any(ProjectDTO.class));
}

@Test
void testUpdateDescription_ServiceError() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();

    when(projectService.updateDescription(any(ProjectDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ProjectDTO> result = projectController.updateDescription(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateDescription(any(ProjectDTO.class));
}

@Test
void testUpdateDescription_Nominal() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();
    ProjectDTO updatedProject = new ProjectDTO();

    when(projectService.updateDescription(any(ProjectDTO.class))).thenReturn(updatedProject);

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(updatedProject, HttpStatus.OK);

    ResponseEntity<ProjectDTO> result = projectController.updateDescription(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateDescription(any(ProjectDTO.class));
}

// Similar tests for updateValidation method

@Test
void testUpdateValidation_UnexpectedException() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();

    when(projectService.updateValidation(any(ProjectDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    ResponseEntity<ProjectDTO> result = projectController.updateValidation(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateValidation(any(ProjectDTO.class));
}

@Test
void testUpdateValidation_ServiceError() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();

    when(projectService.updateValidation(any(ProjectDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ProjectDTO> result = projectController.updateValidation(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateValidation(any(ProjectDTO.class));
}

@Test
void testUpdateValidation_Nominal() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();
    ProjectDTO updatedProject = new ProjectDTO();

    when(projectService.updateValidation(any(ProjectDTO.class))).thenReturn(updatedProject);

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(updatedProject, HttpStatus.OK);

    ResponseEntity<ProjectDTO> result = projectController.updateValidation(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateValidation(any(ProjectDTO.class));
}

@Test
void testGetAllProjects_Nominal() throws CustomRuntimeException {
    List<ProjectDTO> projects = Arrays.asList(new ProjectDTO(), new ProjectDTO());

    when(projectService.listAllProjects()).thenReturn(projects);

    ResponseEntity<List<ProjectDTO>> expectedAnswer = new ResponseEntity<>(projects, HttpStatus.OK);

    ResponseEntity<List<ProjectDTO>> result = projectController.getAllProjects();

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).listAllProjects();
}

@Test
void testGetAllProjects_ServiceError() throws CustomRuntimeException {
    when(projectService.listAllProjects()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    ResponseEntity<List<ProjectDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<List<ProjectDTO>> result = projectController.getAllProjects();

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).listAllProjects();
}

@Test
void testGetAllProjects_UnexpectedException() throws CustomRuntimeException {
    when(projectService.listAllProjects()).thenThrow(new CustomRuntimeException("Unexpected error"));

    ResponseEntity<List<ProjectDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    ResponseEntity<List<ProjectDTO>> result = projectController.getAllProjects();

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).listAllProjects();
}

@Test
    void testGetProjectId_Success() throws CustomRuntimeException {
        // Given
        when(projectService.getProjectId(any(Integer.class))).thenReturn(new ProjectDTO());

        // When
        ResponseEntity<ProjectDTO> response = projectController.getProjectId(1);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetProjectId_ProjectNotFound() throws CustomRuntimeException {
        // Given
        when(projectService.getProjectId(any(Integer.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND));

        // When
        ResponseEntity<ProjectDTO> response = projectController.getProjectId(1);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetProjectId_ServiceError() throws CustomRuntimeException {
        // Given
        when(projectService.getProjectId(any(Integer.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // When
        ResponseEntity<ProjectDTO> response = projectController.getProjectId(1);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }





}
