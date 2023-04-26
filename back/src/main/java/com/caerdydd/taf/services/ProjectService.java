package com.caerdydd.taf.services;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
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

    public ProjectDTO getProjectById(Integer projectId) throws CustomRuntimeException {
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
        return modelMapper.map(projectEntity, ProjectDTO.class);
    }
    
    
}
