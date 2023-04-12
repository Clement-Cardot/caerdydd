package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
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
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;
    
    public List<TeamDTO> listAllTeams() {
        return teamRepository.findAll().stream()
        .map(user -> modelMapper.map(user, TeamDTO.class))
        .collect(Collectors.toList()) ;
    }

    public TeamDTO getTeamById(Integer id) throws NoSuchElementException {
        Optional<TeamEntity> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isEmpty()) {
            throw new NoSuchElementException();
        }
        return modelMapper.map(optionalTeam.get(), TeamDTO.class);
    }

    public void saveTeam(TeamDTO team) {
        TeamEntity teamEntity = modelMapper.map(team, TeamEntity.class);

        teamRepository.save(teamEntity);
    }

    public void applyInATeam(Integer idTeam, Integer idUser) throws CustomRuntimeException {
        // Check if the user and the team exists
        UserDTO user;
        try {
            user = userService.getUserById(idUser);
            getTeamById(idTeam);
        } catch (NoSuchElementException e) {
            logger.warn("User {} or Team {} not found", idUser, idTeam);
            throw new CustomRuntimeException("User not found");
        }

        // Check if the current user is the same as the user to update
        if(Boolean.FALSE.equals(securityConfig.checkCurrentUser(idUser))){
            logger.warn("ILLEGAL API USE : Current user : {} tried to apply in team {} for user {}", securityConfig.getCurrentUser().getId(), idTeam, idUser);
            throw new CustomRuntimeException("Can't apply in a team for another user");
        }

        // Check if the user is already in a team
        if (userService.getUserById(idUser).getTeamMember() != null) {
            Integer idTeamAlreadyIn = userService.getUserById(idUser).getTeamMember().getTeam().getIdTeam();
            logger.warn("ILLEGAL API USE : Current user : {} tried to apply in team {} but is already in team {}", idTeam, idUser, idTeamAlreadyIn);
            throw new CustomRuntimeException("User is already in a team");
        }

        // If everythings OK : create the user role "team_member" and create a new team member entity
        logger.info("Create role of User {} : team_member", idUser);
        RoleDTO newRole = new RoleDTO();
        newRole.setRole("TEAM_MEMBER_ROLE");
        newRole.setUser(user);

        logger.info("Create a new TeamMemberEntity link to User {} and Team {}", idUser, idTeam);
        TeamMemberDTO teamMember = new TeamMemberDTO();
        teamMember.setUser(user);
        teamMember.setTeam(getTeamById(idTeam));

        user.getRoleEntities().add(newRole);
        user.setTeamMember(teamMember);

        logger.info("Save modifications ...");
        userService.saveUser(user);
        logger.info("Modifications saved !");
    }

    public void createTeams(Integer nbTeams) throws CustomRuntimeException{
        if(nbTeams % 2 == 0) { 
            for (int i = 0; i < nbTeams; i++) {
                TeamDTO team = new TeamDTO();
                team.setName("Team " + i);
                //TO-DO : création paires d'équipes
                saveTeam(team);
            }
        }
        else {
            logger.warn("ILLEGAL API USE : Can't create an odd number of teams");
            throw new CustomRuntimeException("Can't create an odd number of teams");
        }
    }

}
