package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.services.TeamMemberService;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {
    
    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping("")
    public ResponseEntity<List<TeamMemberDTO>> list() {
        try {
            List<TeamMemberDTO> users = teamMemberService.listAllTeamMembers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> get(@PathVariable Integer id) {
        try {
            TeamMemberDTO user = teamMemberService.getTeamMemberById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
