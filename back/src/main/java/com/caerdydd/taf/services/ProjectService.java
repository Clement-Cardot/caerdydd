package com.caerdydd.taf.services;

import com.caerdydd.taf.exceptions.ResourceNotFoundException;
import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;

    public List<ProjectDTO> listAllProjects() throws ResourceNotFoundException {
        try {
            return projectRepository.findAll().stream()
                    .map(project -> modelMapper.map(project, ProjectDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResourceNotFoundException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public ProjectDTO saveProject(ProjectDTO project) {
        ProjectEntity projectEntity = modelMapper.map(project, ProjectEntity.class);

        ProjectEntity response = projectRepository.save(projectEntity);

        return modelMapper.map(response, ProjectDTO.class);
    }

    public ProjectDTO[] createProjects(Integer nbProjects) {
        ProjectDTO[] projects = new ProjectDTO[nbProjects];
        for(int i = 0; i < nbProjects; i++) {
            ProjectDTO project = new ProjectDTO();
            project.setName("Project " + (i+1));
            project.setDescription("Description " + (i+1));
            project.setIsValidated(false);
            projects[i] = saveProject(project);
        }
        return projects;
    }
}
