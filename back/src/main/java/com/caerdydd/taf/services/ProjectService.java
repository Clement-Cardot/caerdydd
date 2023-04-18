package com.caerdydd.taf.services;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import com.caerdydd.taf.security.CustomRuntimeException;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.caerdydd.taf.security.SecurityConfig;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper; 

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    SecurityConfig securityConfig;


    public ProjectDTO updateProject(ProjectDTO projectDTO) throws CustomRuntimeException {
        Integer projectId = projectDTO.getIdProject();
        Optional<ProjectEntity> optionalProject;
        try {
            optionalProject = projectRepository.findById(projectId);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    
        if (optionalProject.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND);
        }
    
        ProjectEntity projectEntity = optionalProject.get();
    
        // Get the team associated with the project
        Optional<TeamEntity> optionalTeam = teamRepository.findByProjectDevId(projectId);

        if (optionalTeam.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND);
        }

        TeamEntity teamEntity = optionalTeam.get();
    
        // Verify if the current user is a member of the associated team with the project
        Integer currentUserId = securityConfig.getCurrentUser().getId();
        boolean isUserInAssociatedTeam = teamEntity.getTeamMembers().stream()
                .anyMatch(teamMember -> teamMember.getUser().getId().equals(currentUserId));
    
        if (!isUserInAssociatedTeam) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_IN_ASSOCIATED_TEAM);
        }
    
        projectEntity.setName(projectDTO.getName());
        projectEntity.setDescription(projectDTO.getDescription());
    
        ProjectEntity updatedProjectEntity = projectRepository.save(projectEntity);
    
        return modelMapper.map(updatedProjectEntity, ProjectDTO.class);
    }

    public ProjectDTO saveProject(ProjectDTO project) {
        ProjectEntity projectEntity = modelMapper.map(project, ProjectEntity.class);

        ProjectEntity response = projectRepository.save(projectEntity);

        return modelMapper.map(response, ProjectDTO.class);
    }

    public List<ProjectDTO> createProjects(Integer nbProjects, Integer nbProjectsInitial) {
        List<ProjectDTO> projects = new ArrayList<ProjectDTO>();
        for(int i = nbProjectsInitial; i < nbProjectsInitial + nbProjects; i++) {
            ProjectDTO project = new ProjectDTO();
            project.setIdProject(i+1);
            project.setName("Projet " + (i+1));
            project.setDescription("Description " + (i+1));
            project.setIsValidated(false);
            projects.add(saveProject(project));
        }
        return projects;
    }
}
