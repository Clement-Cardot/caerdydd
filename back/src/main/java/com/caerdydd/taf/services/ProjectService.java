package com.caerdydd.taf.services;

import com.caerdydd.taf.exceptions.ResourceNotFoundException;
import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;
    

    @Transactional
    public ProjectDTO updateProject(Integer teamId, Integer userId, ProjectDTO projectDTO) throws ResourceNotFoundException {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + teamId));

        // Vérifier si l'utilisateur est membre de l'équipe associée à l'id_project_dev
        boolean isUserInTeam = teamEntity.getTeamMembers().stream()
                .anyMatch(member -> member.getIdUser().equals(userId) && member.getTeam().getIdTeam().equals(teamId));
        if (!isUserInTeam) {
            throw new ResourceNotFoundException("User is not a member of the team with id " + teamId);
        }

        ProjectEntity projectEntity = projectRepository.findById(teamEntity.getProjectDev().getIdProject())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + teamEntity.getProjectDev().getIdProject()));

        projectEntity.setName(projectDTO.getName());
        projectEntity.setDescription(projectDTO.getDescription());

        projectRepository.save(projectEntity);

        return projectDTO;
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
            project.setName("Project " + i+1);
            project.setDescription("Description " + i+1);
            project.setIsValidated(false);
            projects[i] = saveProject(project);
        }
        return projects;
    }
}
