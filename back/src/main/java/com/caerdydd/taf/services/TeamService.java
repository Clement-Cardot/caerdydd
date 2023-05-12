package com.caerdydd.taf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
import com.caerdydd.taf.services.rules.TeamServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@Service
@Transactional
public class TeamService {

    private static final Logger logger = LogManager.getLogger(TeamService.class);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    private TeamServiceRules teamServiceRules;

    @Autowired
    private UserServiceRules userServiceRules;
    
    public List<TeamDTO> listAllTeams() throws CustomRuntimeException {
        try {
            return teamRepository.findAll().stream()
                    .map(user -> modelMapper.map(user, TeamDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        
    }

    public TeamDTO getTeamById(Integer id) throws CustomRuntimeException {
        Optional<TeamEntity> optionalTeam;
        try {
            optionalTeam = teamRepository.findById(id);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        
        if (optionalTeam.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND);
        }
        return modelMapper.map(optionalTeam.get(), TeamDTO.class);
    }

    public TeamDTO saveTeam(TeamDTO team) {
        TeamEntity teamEntity = modelMapper.map(team, TeamEntity.class);

        TeamEntity response = teamRepository.save(teamEntity);

        return modelMapper.map(response, TeamDTO.class);
    }

    public List<TeamDTO> createTeams(Integer nbTeamsPairs) throws CustomRuntimeException{
        
        if(nbTeamsPairs < 1) {
            throw new CustomRuntimeException(CustomRuntimeException.NB_TEAMS_INVALID);
        }
        // Check if the user is an option leader
        userServiceRules.checkUserRole(securityConfig.getCurrentUser(), "OPTION_LEADER_ROLE");

        //If everyting is ok, create the teams and projects
        int nbTeams = nbTeamsPairs * 2;
        int nbTeamsInitial = this.listAllTeams().size();
        List<TeamDTO> teams = new ArrayList<>();
        List<ProjectDTO> projects = projectService.createProjects(nbTeams, nbTeamsInitial);
        

        for (int i = nbTeamsInitial; i < nbTeamsInitial + nbTeams; i++) {
            TeamDTO team = new TeamDTO();
            team.setIdTeam(i + 1);
            team.setName("Équipe " + (i + 1));
            team.setProjectDev(projects.get(i-nbTeamsInitial));
            projects.get(i-nbTeamsInitial).setTeamDev(team);
            if (i % 2 == 0) {
                team.setProjectValidation(projects.get(i+1-nbTeamsInitial));
                projects.get(i+1-nbTeamsInitial).setTeamValidation(team);
            }
            else {
                team.setProjectValidation(projects.get(i-1-nbTeamsInitial));
                projects.get(i-1-nbTeamsInitial).setTeamValidation(team);
            }
            saveTeam(team);
            teams.add(team);
        }
        return teams;
    }

    public UserDTO applyInATeam(Integer idTeam, Integer idUser) throws CustomRuntimeException {
        // Check if the user and the team exists
        UserDTO user = userService.getUserById(idUser);
        TeamDTO team  = this.getTeamById(idTeam);

        // Check if the user is a Student
        userServiceRules.checkUserRole(user, "STUDENT_ROLE");

        // Check if the current user is the same as the user to update
        userServiceRules.checkCurrentUser(user);

        // Check if the team is full
        teamServiceRules.checkTeamIsFull(team);

        // check if the speciality ratio is respected (2CSS/4LD)
        teamServiceRules.checkSpecialityRatio(team);

        // If everythings OK : create the user role "team_member" and create a new team member entity
        logger.info("Create role of User {} : team_member", idUser);
        RoleDTO newRole = new RoleDTO();
        newRole.setRole("TEAM_MEMBER_ROLE");
        newRole.setUser(user);

        logger.info("Create a new TeamMemberEntity link to User {} and Team {}", idUser, idTeam);
        TeamMemberDTO teamMember = new TeamMemberDTO();
        teamMember.setUser(user);
        teamMember.setTeam(team);

        logger.info("Create a new Role 'TeamMember' and delete role 'Student' link to User {}", idUser);
        // Get the role equal to STUDENT_ROLE and delete it
        Optional<RoleDTO> roleToRemove = user.getRoles().stream().filter(role -> role.getRole().equals(RoleDTO.STUDENT_ROLE)).findFirst();
        if (roleToRemove.isPresent()){
            user.getRoles().remove(roleToRemove.get());
        } else {
            logger.warn("ILLEGAL API USE : Current user : {} tried to apply in team {} but is not a student", idUser, idTeam);
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_STUDENT);
        }
        // Add the new role
        user.getRoles().add(newRole);

        // Set team member entity
        user.setTeamMember(teamMember);

        logger.info("Save modifications ...");
        UserDTO response = userService.updateUser(user);
        roleService.deleteRole(roleToRemove.get());
        logger.info("Modifications saved !");
        return response;
    }


    public TeamDTO addTestBookLink(Integer idTeam, String testBookLink) throws CustomRuntimeException {
        TeamDTO teamDTO = getTeamById(idTeam);
        TeamEntity teamEntity = modelMapper.map(teamDTO, TeamEntity.class); // Convertissez TeamDTO en TeamEntity
        teamEntity.setTestBookLink(testBookLink);
        teamRepository.save(teamEntity); // Enregistrez l'entité TeamEntity
        return modelMapper.map(teamEntity, TeamDTO.class); // Convertissez l'entité TeamEntity mise à jour en TeamDTO et retournez-la
    }
    

    public String getTestBookLinkDev(Integer idTeam) throws CustomRuntimeException {
        TeamDTO team = getTeamById(idTeam);
        return team.getTestBookLink();
    }
    
    public String getTestBookLinkValidation(Integer idTeam) throws CustomRuntimeException {
        TeamDTO team = getTeamById(idTeam);
        TeamDTO pairedTeam = getTeamById(team.getProjectValidation().getIdProject());
        return pairedTeam.getTestBookLink();
    }

}
