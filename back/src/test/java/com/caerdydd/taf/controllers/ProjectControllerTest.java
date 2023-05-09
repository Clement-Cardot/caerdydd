package com.caerdydd.taf.controllers;
import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ProjectService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
// @WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

@InjectMocks
private ProjectController projectController;

@Mock
private ProjectService projectService;

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @MockBean
//     private ProjectService projectService;

//     private ProjectDTO projectDTO;

//     @BeforeEach
//     public void setUp() {
//         projectDTO = new ProjectDTO();
//         projectDTO.setIdProject(1);
//         projectDTO.setName("Test Project");
//         projectDTO.setDescription("Test Description");
//     }

//     @Test
//     @WithMockUser
//     public void testGetProjectById() throws Exception {
//         when(projectService.getProjectById(1)).thenReturn(projectDTO);

//         mockMvc.perform(get("/api/projects/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.idProject").value(1))
//                 .andExpect(jsonPath("$.name").value("Test Project"))
//                 .andExpect(jsonPath("$.description").value("Test Description"));
//     }

//     @Test
//     @WithMockUser
//     public void testGetProjectById_NotFound() throws Exception {
//         when(projectService.getProjectById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND));

//         mockMvc.perform(get("/api/projects/1"))
//                 .andExpect(status().isNotFound());
//     }

//     @Test
//     @WithMockUser(authorities = "TEAM_MEMBER_ROLE")
//     public void testUpdateProject() throws Exception {
//     when(projectService.updateProject(projectDTO)).thenReturn(projectDTO);

//     mockMvc.perform(put("/api/projects")
//             .contentType(MediaType.APPLICATION_JSON)
//             .content(objectMapper.writeValueAsString(projectDTO)))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.idProject").value(1))
//             .andExpect(jsonPath("$.name").value("Test Project"))
//             .andExpect(jsonPath("$.description").value("Test Description"));
// }




//     @Test
//     public void testUpdateProject_Forbidden() throws Exception {
//         when(projectService.updateProject(projectDTO)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_IN_ASSOCIATED_TEAM));

//         mockMvc.perform(put("/api/projects")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(projectDTO)))
//                 .andExpect(status().isForbidden());
//     }



@Test
    public void testUpdateProject_Nominal() throws CustomRuntimeException {
        ProjectDTO projectToUpdate = new ProjectDTO();
        ProjectDTO updatedProject = new ProjectDTO();

        when(projectService.updateProject(any(ProjectDTO.class))).thenReturn(updatedProject);

        ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(updatedProject, HttpStatus.OK);

        ResponseEntity<ProjectDTO> result = projectController.updateDescription(projectToUpdate);

        assertEquals(expectedAnswer, result);
        verify(projectService, times(1)).updateProject(any(ProjectDTO.class));
    }

    @Test
    public void testGetProject_Nominal() throws CustomRuntimeException {
        Integer projectId = 1;
        ProjectDTO project = new ProjectDTO();

        when(projectService.getProjectById(projectId)).thenReturn(project);

        ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(project, HttpStatus.OK);

        ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

        assertEquals(expectedAnswer, result);
        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
public void testUpdateProject_ServiceError() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();

    when(projectService.updateProject(any(ProjectDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ProjectDTO> result = projectController.updateDescription(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateProject(any(ProjectDTO.class));
}

@Test
public void testUpdateProject_UnexpectedException() throws CustomRuntimeException {
    ProjectDTO projectToUpdate = new ProjectDTO();

    when(projectService.updateProject(any(ProjectDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    ResponseEntity<ProjectDTO> result = projectController.updateDescription(projectToUpdate);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).updateProject(any(ProjectDTO.class));
}

@Test
public void testGetProject_ProjectNotFound() throws CustomRuntimeException {
    Integer projectId = 1;

    when(projectService.getProjectById(projectId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).getProjectById(projectId);
}

@Test
public void testGetProject_ServiceError() throws CustomRuntimeException {
    Integer projectId = 1;

    when(projectService.getProjectById(projectId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).getProjectById(projectId);
}

@Test
public void testGetProject_UnexpectedException() throws CustomRuntimeException {
    Integer projectId = 1;

    when(projectService.getProjectById(projectId)).thenThrow(new CustomRuntimeException("Unexpected error"));

    ResponseEntity<ProjectDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    ResponseEntity<ProjectDTO> result = projectController.getProject(projectId);

    assertEquals(expectedAnswer, result);
    verify(projectService, times(1)).getProjectById(projectId);
}



}
