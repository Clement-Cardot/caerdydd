package com.caerdydd.taf.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeamMemberService;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    private static final Logger logger = LogManager.getLogger(TeamMemberController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";
    
    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("")
    public ResponseEntity<List<TeamMemberDTO>> list() {
        logger.info("Process request : List all team members");
        try {
            List<TeamMemberDTO> users = teamMemberService.listAllTeamMembers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> get(@PathVariable Integer id) {
        logger.info("Process request : Get team member by id : {}", id);
        try {
            TeamMemberDTO user = teamMemberService.getTeamMemberById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.USER_NOT_FOUND)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/bonus/{id}/{bonus}")
    public ResponseEntity<TeamMemberDTO> setBonusPenalty(@PathVariable Integer id, @PathVariable Integer bonus) {
        logger.info("Process request : Set bonus/penalty for team member by id : {}", id);
        try {
            TeamMemberDTO teamMember = teamMemberService.setBonusPenaltyById(id, bonus);
            return new ResponseEntity<>(teamMember, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
    @PostMapping("/individualMark")
    public ResponseEntity<TeamMemberDTO> setIndividualMarkById(@RequestParam("teamMemberId") Integer id, @RequestParam("mark") Integer individualMark) {
        logger.info("Process request : Set individualMark for team member by id : {}", id);
        try {
            TeamMemberDTO teamMember = teamMemberService.setIndividualMarkById(id, individualMark);
            return new ResponseEntity<>(teamMember, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (e.getMessage().equals(CustomRuntimeException.USER_IS_NOT_A_JURY_MEMBER)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
