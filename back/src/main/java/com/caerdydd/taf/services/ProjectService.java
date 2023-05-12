package com.caerdydd.taf.services;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.UserServiceRules;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Optional;

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
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    UserServiceRules userServiceRules;


    public List<ProjectDTO> listAllProjects() throws CustomRuntimeException {
        try {
            return projectRepository.findAll().stream()
                    .map(project -> modelMapper.map(project, ProjectDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
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

    public ProjectDTO updateProject(ProjectDTO projectDTO) throws CustomRuntimeException {
        ProjectEntity projectEntity = modelMapper.map(projectDTO, ProjectEntity.class);
        
        Optional<ProjectEntity> optionalUser = projectRepository.findById(projectEntity.getIdProject());
        if (optionalUser.isEmpty()){
            throw new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND);
        }

        ProjectEntity response = null;
        try {
            response = projectRepository.save(projectEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, ProjectDTO.class);
    }
    
    public ProjectDTO updateValidation(ProjectDTO projectDTO) throws CustomRuntimeException {
        // Verify if the current user is an option leader
        userServiceRules.checkCurrentUserRole("OPTION_LEADER_ROLE");

        return this.updateProject(projectDTO);
    }

    public ProjectDTO updateDescription(ProjectDTO projectDTO) throws CustomRuntimeException {
        // Verify if the current user is a team member
        userServiceRules.checkCurrentUserRole("TEAM_MEMBER_ROLE");

        // Get current user
        UserDTO currentUser = userServiceRules.getCurrentUser();

        // Verify he is a member of the team
        Optional<TeamEntity> optionalTeam = teamRepository.findByProjectDevId(projectDTO.getIdProject());
        if (optionalTeam.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND);
        }
        TeamEntity team = optionalTeam.get();

        TeamMemberDTO teamMember = teamMemberService.getTeamMemberById(currentUser.getId());

        if (!teamMember.getTeam().getIdTeam().equals(team.getIdTeam())) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_IN_A_TEAM);
        }

        return this.updateProject(projectDTO);
    }
}
