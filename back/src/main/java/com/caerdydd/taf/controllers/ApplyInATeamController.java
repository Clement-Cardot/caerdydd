package com.caerdydd.taf.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.Team;
import com.caerdydd.taf.models.TeamMember;
import com.caerdydd.taf.models.UserEntity;
import com.caerdydd.taf.service.TeamMemberService;
import com.caerdydd.taf.service.TeamService;
import com.caerdydd.taf.service.UserService;


@RestController
@RequestMapping("/api/student")
public class ApplyInATeamController {

    private static final Logger logger = LogManager.getLogger(ApplyInATeamController.class);

    @Autowired
    TeamService teamService;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<Map<String, Object>> listAllTeams() {

        List<Map<String, Object>> teamsCompositions = new ArrayList<>();
        
        List<Team> teams = teamService.listAllTeams();
        List<TeamMember> teamMembers = teamMemberService.listAllTeamMembers();
        List<UserEntity> users = userService.listAllUsers();

        for (Team team : teams) {
            Map<String, Object> teamComposition = new HashMap<>();
            teamComposition.put("idTeam", team.getIdTeam());
            teamComposition.put("teamName", team.getName());
            List<Map<String, Object>> teamMembersOfTeam = new ArrayList<>();
            for (TeamMember teamMember : teamMembers) {
                if (teamMember.getIdTeam().equals(team.getIdTeam())) {
                    
                    UserEntity user = users.get(teamMember.getIdUser());
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("id", user.getId());
                    userData.put("firstName", user.getName());
                    userData.put("lastName", user.getSurname());
                    userData.put("email", user.getEmail());
                    
                    teamMembersOfTeam.add(userData);
                }
            }
            teamComposition.put("teamMembers", teamMembersOfTeam);

            teamsCompositions.add(teamComposition);
        }
        return teamsCompositions;
    }

    @PutMapping("/{idTeam}/{idUser}/{speciality}")
    public void applyInATeam(@PathVariable Integer idTeam, @PathVariable Integer idUser, @PathVariable String speciality) {

        logger.info("Request from user {} to apply in team {}", idUser, idTeam);

        try {
            teamMemberService.getTeamMemberById(idUser);
            logger.info("TeamMember already exist... cannot apply to an other team !");

        } catch (NotFoundException e) {

            logger.info("TeamMember not found... apply is authorized !");

            logger.info("Updating user role to team_member...");
            UserEntity user = userService.getUserById(idUser);
            user.setRole("team_member");
            userService.saveUser(user);

            logger.info("Creating new TeamMember...");
            TeamMember teamMember = new TeamMember(user.getId(), idTeam, speciality);
            teamMemberService.saveTeamMember(teamMember);
        }


    }
}
