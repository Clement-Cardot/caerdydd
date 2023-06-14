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

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
import com.caerdydd.taf.services.rules.JuryServiceRules;
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

    @Autowired
    private JuryServiceRules juryServiceRules;

    @Autowired
    private NotificationService notificationService;
    
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


    public TeamDTO addTestBookLink(TeamDTO team) throws CustomRuntimeException {
        // Vérifie si l'équipe existe
        TeamDTO teamDTO = getTeamById(team.getIdTeam());

        // Vérifie si l'utilisateur est un membre de l'équipe
        userServiceRules.checkCurrentUserRole("TEAM_MEMBER_ROLE");
        teamServiceRules.checkIfUserIsMemberOfTeam(teamDTO);

        // Vérifie si le lien est valide
        teamServiceRules.isValidLink(team.getTestBookLink());

        teamDTO.setTestBookLink(team.getTestBookLink());

        // Récupère l'équipe pair
        TeamDTO pairTeam = getTeamById(teamDTO.getProjectValidation().getIdProject());

        // Crée et envoie une notification à chaque membre de l'équipe pair
        for (TeamMemberDTO member : pairTeam.getTeamMembers()) {
            UserDTO user = member.getUser();
            NotificationDTO notification = new NotificationDTO();
            notification.setUser(user);
            notification.setMessage(team.getName() + " a déposé un nouveau lien pour le cahier de validation");
            notification.setIsRead(false);
            notificationService.createNotification(notification);
        }

        return saveTeam(teamDTO);
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
    
    public TeamDTO setTeamWorkMarkById(Integer id, Integer teamWorkMark)throws CustomRuntimeException{
        // Check if the current user is a jury member 
        userServiceRules.checkCurrentUserRole("JURY_MEMBER_ROLE");

        // Check if the value of the bonus is correct.
        TeamServiceRules.checkTeamWorkMark(teamWorkMark);


        TeamDTO team = getTeamById(id);

        team.setTeamWorkMark(teamWorkMark);
        return saveTeam(team);
    }


    public TeamDTO setTeamValidationMarkById(Integer id, Integer teamValidationMark)throws CustomRuntimeException{
        // Check if the current user is a jury member 
        userServiceRules.checkCurrentUserRole("JURY_MEMBER_ROLE");

        // Check if the value of the bonus is correct.
        TeamServiceRules.checkTeamValidationMark(teamValidationMark);

        TeamDTO team = getTeamById(id);

        team.setTeamValidationMark(teamValidationMark);
        return saveTeam(team);
    }
        
    public TeamDTO setCommentOnReport(Integer idTeam, String comment) throws CustomRuntimeException {
        userServiceRules.checkCurrentUserRole(RoleDTO.JURY_MEMBER_ROLE);
        TeamDTO team = this.getTeamById(idTeam);
        juryServiceRules.checkJuryMemberManageTeam(userServiceRules.getCurrentUser().getId(), team);
        team.setReportComments(comment);
        return saveTeam(team);
    }
}


