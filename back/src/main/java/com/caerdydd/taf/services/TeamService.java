package com.caerdydd.taf.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

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
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;
    
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

    public UserDTO applyInATeam(Integer idTeam, Integer idUser) throws CustomRuntimeException {
        // Check if the user and the team exists
        UserDTO user;
        TeamDTO team;
        try {
            user = userService.getUserById(idUser);
            team = getTeamById(idTeam);
        } catch (CustomRuntimeException e) {
            logger.warn("User {} or Team {} not found", idUser, idTeam);
            throw e;
        }

        // Check if the user is a Student
        if(user.getRoles().stream().noneMatch(role -> role.getRole().equals(RoleDTO.STUDENT_ROLE))){
            logger.warn("ILLEGAL API USE : Current user : {} tried to apply in team {} but is not a student", idUser, idTeam);
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_STUDENT);
        }

        // Check if the current user is the same as the user to update
        if(Boolean.FALSE.equals(securityConfig.checkCurrentUser(idUser))){
            logger.warn("ILLEGAL API USE : Current user is not Request User");
            throw new CustomRuntimeException(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER);
        }

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

}
