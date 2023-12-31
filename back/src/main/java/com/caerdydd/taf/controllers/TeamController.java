package com.caerdydd.taf.controllers;

import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.FileService;
import com.caerdydd.taf.services.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private static final Logger logger = LogManager.getLogger(TeamController.class);
    static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    private TeamService teamService;

    @Autowired
    private FileService fileService;
    
    @GetMapping("")
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        logger.info("Process request : Get all teams");
        try {
            List<TeamDTO> users = teamService.listAllTeams();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
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

    @GetMapping("/{idTeam}/teamMembers")
    public ResponseEntity<List<TeamMemberDTO>> getAllTeamMembersOfTeamById(@PathVariable Integer idTeam) {
        logger.info("Process request : Get all members of team by id : {}", idTeam);
        try {
            List<TeamMemberDTO> teamMembers = teamService.getTeamById(idTeam).getTeamMembers();
            return new ResponseEntity<>(teamMembers, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
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

    @PutMapping("/createTeams")
    public ResponseEntity<List<TeamDTO>> createTeams(@RequestBody Integer nbTeamsPairs) {
        logger.info("Process request : Create {} teams", nbTeamsPairs*2);
        try {
            List<TeamDTO> response = teamService.createTeams(nbTeamsPairs);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch(e.getMessage()) {
            case CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER:
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case CustomRuntimeException.NB_TEAMS_INVALID:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            case CustomRuntimeException.SERVICE_ERROR:
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
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
            case CustomRuntimeException.USER_IS_NOT_A_STUDENT:
            case CustomRuntimeException.TEAM_IS_FULL:
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            case CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS:
            case CustomRuntimeException.TEAM_ALREADY_HAS_4_LD:
            case CustomRuntimeException.USER_ALREADY_IN_A_TEAM:
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            case CustomRuntimeException.USER_NOT_FOUND:
            case CustomRuntimeException.TEAM_NOT_FOUND:
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            case CustomRuntimeException.SERVICE_ERROR:
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }
    
    @PutMapping("/testBookLink")
    public ResponseEntity<TeamDTO> addTestBookLink(@RequestBody TeamDTO team) {
        logger.info("Process request : Add test book link in team: {} with link {}", team.getIdTeam(), team.getTestBookLink());
        try {
            TeamDTO updatedTeam = teamService.addTestBookLink(team);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.TEAM_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.INVALID_LINK:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @GetMapping("/{idTeam}/testBookLinkDev")
    public ResponseEntity<String> getTestBookLinkDev(@PathVariable Integer idTeam) {
        logger.info("Process request : Get test book link dev of team: {}", idTeam);
        try {
            String testBookLinkDev = teamService.getTestBookLinkDev(idTeam);
            return new ResponseEntity<>(testBookLinkDev, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.TEAM_NOT_FOUND:
                case CustomRuntimeException.LINK_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @GetMapping("/{idTeam}/testBookLinkValidation")
    public ResponseEntity<String> getTestBookLinkValidation(@PathVariable Integer idTeam) {
        logger.info("Process request : Get test book link validation of team: {}", idTeam);
        try {
            String testBookLinkValidation = teamService.getTestBookLinkValidation(idTeam);
            return new ResponseEntity<>(testBookLinkValidation, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.TEAM_NOT_FOUND:
                case CustomRuntimeException.LINK_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @PostMapping("/upload")
	public ResponseEntity<HttpStatus> saveFile(@RequestParam("file") MultipartFile file, @RequestParam("teamId") int id, @RequestParam("fileType") String type) {
        logger.info("Process request : Save file {} for team {} with type {}", file.getOriginalFilename(), id, type);
        try {
            fileService.saveFile(file, id, type);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                case CustomRuntimeException.TEAM_NOT_FOUND:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.INCORRECT_FILE_FORMAT:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @GetMapping("/download/{teamId}/{fileType}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable("teamId") int id, @PathVariable("fileType") String type) {
        try {
            Resource file = fileService.loadFileAsResource(id, type);
            InputStreamResource resource = new InputStreamResource(file.getInputStream());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", "Equipe-" + id + "_" + type + ".pdf");

            return new ResponseEntity<>(resource, headers, HttpStatus.ACCEPTED);
        } catch (CustomRuntimeException e) { 
            switch (e.getMessage()) {
                case CustomRuntimeException.SERVICE_ERROR:
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                case CustomRuntimeException.FILE_NOT_FOUND:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        } catch (IOException e) {
            logger.warn("Could not retrieve the file. Error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     

    @PostMapping("/teamMarks")
    public ResponseEntity<TeamDTO> setTeamMarks(@RequestParam("teamId") Integer id, @RequestParam("teamWorkMark") Integer teamWorkMark,@RequestParam("teamValidationMark") Integer teamValidationMark) {
        logger.info("Process request: Set team marks for team by id: {}", id);
        try {
            teamService.setTeamWorkMarkById(id, teamWorkMark);
            TeamDTO team = teamService.setTeamValidationMarkById(id, teamValidationMark);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.error(e.getMessage());
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

    @PostMapping("/teamWorkMark")
    public ResponseEntity<TeamDTO> setTeamWorkMark(@RequestParam("teamId") Integer id, @RequestParam("teamWorkMark") Integer teamWorkMark) {
        logger.info("Process request : Set teamWorkMark for team by id : {}", id);
        try {
            TeamDTO team = teamService.setTeamWorkMarkById(id, teamWorkMark);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
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
    @PostMapping("/teamValidationMark")
    public ResponseEntity<TeamDTO> setTeamValidationMark(@RequestParam("teamId") Integer id, @RequestParam("teamValidationMark") Integer teamValidationMark) {
        logger.info("Process request : Set teamValidationMark for team by id : {}", id);
        try {
            TeamDTO team = teamService.setTeamValidationMarkById(id, teamValidationMark);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
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
    
    @PutMapping("/setCommentOnReport")
	public ResponseEntity<HttpStatus> setCommentOnReport(@RequestParam("idTeam") int idTeam, @RequestParam("comment") String comment) {
        logger.info("Process request : Add comment to reportfor team {}", idTeam);
        try {
            teamService.setCommentOnReport(idTeam, comment);
            
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                case CustomRuntimeException.TEAM_NOT_FOUND:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.USER_IS_NOT_AUTHORIZED:
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }
}
