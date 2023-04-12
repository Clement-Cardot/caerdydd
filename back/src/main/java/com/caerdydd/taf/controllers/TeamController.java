package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;

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
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;
    
    @GetMapping("")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        try {
            List<TeamDTO> users = teamService.listAllTeams();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idTeam}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Integer idTeam) {
        try {
            TeamDTO user = teamService.getTeamById(idTeam);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idTeam}/teamMembers")
    public ResponseEntity<List<TeamMemberDTO>> getAllTeamMembersOfTeamById(@PathVariable Integer idTeam) {
        try {
            List<TeamMemberDTO> teamMembers = teamService.getTeamById(idTeam).getTeamMembers();
            return new ResponseEntity<>(teamMembers, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idTeam}/{idUser}")
    public ResponseEntity<HttpStatus> applyInATeam(@PathVariable Integer idTeam, @PathVariable Integer idUser) {
        try {
            teamService.applyInATeam(idTeam, idUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
            case "Can't apply in a team for another user":
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case "User is already in a team":
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case "User not found":
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }
}