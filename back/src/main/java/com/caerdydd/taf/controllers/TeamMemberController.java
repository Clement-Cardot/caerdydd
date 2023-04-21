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
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeamMemberService;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    private static final Logger logger = LogManager.getLogger(TeamMemberController.class);
    
    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("")
    public ResponseEntity<List<TeamMemberDTO>> list() {
        logger.info("Process request : List all team members");
        try {
            List<TeamMemberDTO> users = teamMemberService.listAllTeamMembers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> get(@PathVariable Integer id) {
        logger.info("Process request : Get team member by id : {}", id);
        try {
            TeamMemberDTO user = teamMemberService.getTeamMemberById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
