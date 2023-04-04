package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.security.SecurityConfig;
import com.caerdydd.taf.services.TeamMemberService;
import com.caerdydd.taf.services.TeamService;
import com.caerdydd.taf.services.UserService;


@RestController
@RequestMapping("/api/student")
public class EnrollController {

    private static final Logger logger = LogManager.getLogger(EnrollController.class);

    @Autowired
    TeamService teamService;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;

    @GetMapping("")
    public ResponseEntity<List<TeamDTO>> list() {
        try {
            List<TeamDTO> teams = teamService.listAllTeams().stream()
                                .map(team -> modelMapper.map(team, TeamDTO.class))
                                .collect(Collectors.toList());
                                
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idTeam}/{idUser}")
    public ResponseEntity<TeamMemberDTO> applyInATeam(@PathVariable Integer idTeam, @PathVariable Integer idUser) {
        
        // Check if the current user is the same as the user to update
        if(Boolean.FALSE.equals(securityConfig.checkCurrentUser(idUser))){
            logger.warn("ILLEGAL API USE : Current user : {} tried to apply in team {} for user {}", securityConfig.getCurrentUser().getId(), idTeam, idUser);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        logger.info("Request from user {} to apply in team {}", idUser, idTeam);

        // Check if the user is already a team member
        try {
            teamMemberService.getTeamMemberById(idUser);
            logger.warn("ILLEGAL API USE : TeamMember already exist for this user : {}", idUser);
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

            // If not, update the user role to "team_member" and create a new team member entity
        } catch (NoSuchElementException e) {

            logger.info("Update role of User {} to team_member", idUser);
            UserEntity user = userService.getUserById(idUser);
            user.setRole("team_member");
            userService.saveUser(user);

            logger.info("Create a new TeamMemberEntity link to User {} and Team {}", idUser, idTeam);
            TeamMemberEntity teamMember = new TeamMemberEntity();
            teamMember.setUser(user);
            teamMember.setTeam(teamService.getTeamById(idTeam));
            teamMemberService.saveTeamMember(teamMember);

            // Return the new team member entity
            return new ResponseEntity<>(modelMapper.map(teamMember, TeamMemberDTO.class), HttpStatus.OK);
        }


    }
}
