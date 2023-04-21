package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    
    @InjectMocks
    private ProjectService projectService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ProjectRepository projectRepository;
    
    // @Test
    // void testSaveProject_Nominal() {
    //     // Mock projectRepository.save() method
    //     ProjectEntity mockedAnswer = new ProjectEntity("Projet A", "Description 1");
    //     when(projectRepository.save(any(ProjectEntity.class))).thenReturn(mockedAnswer);

    //     // Prepare the input
    //     ProjectDTO projectToSave = new ProjectDTO("Projet A", "Description 1");
        
    //     // Call the method to test
    //     ProjectDTO result = projectService.saveProject(projectToSave);

    //     // Check the result
    //     verify(projectRepository, times(1)).save(any(ProjectEntity.class));
    //     assertEquals(projectToSave.toString(), result.toString());
    // }

    @Test
    void testCreateProject_Nominal() throws CustomRuntimeException {
        // Mock projectRepository.save() method
        when(projectRepository.save(any(ProjectEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // Definie the expected answer
        ProjectDTO project1 = new ProjectDTO();
        project1.setIdProject(1);
        project1.setName("Projet 1");
        project1.setDescription("Description 1");
        project1.setIsValidated(false);
        ProjectDTO project2 = new ProjectDTO();
        project2.setIdProject(2);
        project2.setName("Projet 2");
        project2.setDescription("Description 2");
        project2.setIsValidated(false);
        List<ProjectDTO> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(project1);
        expectedAnswer.add(project2);

        // Call the method to test
        List<ProjectDTO> result = projectService.createProjects(2,0);

        // Check the result
        verify(projectRepository, times(2)).save(any(ProjectEntity.class));
        assertEquals(expectedAnswer.toString(), result.toString());

    }
}
