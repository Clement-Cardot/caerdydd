package com.caerdydd.taf.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private static final Logger logger = LogManager.getLogger(TeamController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    private TeamService teamService;
    
    @GetMapping("")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        logger.info("Process request : Get all teams");
        try {
            List<TeamDTO> users = teamService.listAllTeams();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/{idTeam}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Integer idTeam) {
        logger.info("Process request : Get team by id : {}", idTeam);
        try {
            TeamDTO user = teamService.getTeamById(idTeam);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/{idTeam}/teamMembers")
    public ResponseEntity<List<TeamMemberDTO>> getAllTeamMembersOfTeamById(@PathVariable Integer idTeam) {
        logger.info("Process request : Get all members of team by id : {}", idTeam);
        try {
            List<TeamMemberDTO> teamMembers = teamService.getTeamById(idTeam).getTeamMembers();
            return new ResponseEntity<>(teamMembers, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PutMapping("/createTeams")
    public ResponseEntity<List<TeamDTO>> createTeams(@RequestBody Integer nbTeamsPairs) {
        logger.info("Process request : Create {} teams", nbTeamsPairs*2);
        try {
            List<TeamDTO> response = teamService.createTeams(nbTeamsPairs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        } 
    }

    @PutMapping("/{idTeam}/{idUser}")
    public ResponseEntity<UserDTO> applyInATeam(@PathVariable Integer idTeam, @PathVariable Integer idUser) {
        logger.info("Process request : Apply in team: {} with user {}", idTeam, idUser);
        try {
            UserDTO updatedUser = teamService.applyInATeam(idTeam, idUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
            case CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case CustomRuntimeException.USER_IS_NOT_A_STUDENT:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case CustomRuntimeException.TEAM_IS_FULL:
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS:
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            case CustomRuntimeException.TEAM_ALREADY_HAS_4_LD:
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            case CustomRuntimeException.USER_ALREADY_IN_A_TEAM:
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            case CustomRuntimeException.USER_NOT_FOUND:
                logger.warn("User not found", e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case CustomRuntimeException.TEAM_NOT_FOUND:
                logger.warn("Team not found", e);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case CustomRuntimeException.SERVICE_ERROR:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }
}
