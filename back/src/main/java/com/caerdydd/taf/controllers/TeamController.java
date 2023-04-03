package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.services.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping("")
    public ResponseEntity<List<TeamDTO>> list() {
        try {
            List<TeamDTO> users = teamService.listAllTeams().stream()
                                .map(user -> modelMapper.map(user, TeamDTO.class))
                                .collect(Collectors.toList()) ;
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> get(@PathVariable Integer id) {
        try {
            TeamDTO user = modelMapper.map(teamService.getTeamById(id), TeamDTO.class);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
