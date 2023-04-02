package com.caerdydd.taf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.Team;
import com.caerdydd.taf.service.TeamService;
import com.caerdydd.taf.service.UserService;


@RestController
@RequestMapping("/api/student")
public class ApplyInATeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<Team>> listAllTeams() {
        return ResponseEntity.ok(teamService.listAllTeams());
    }
}
