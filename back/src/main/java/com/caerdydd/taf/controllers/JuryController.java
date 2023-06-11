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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.services.JuryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@RestController
@RequestMapping("/api/juries")
public class JuryController {

    private static final Logger logger = LogManager.getLogger(JuryController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    private JuryService juryService;
    
    @PutMapping("/add/{juryLD}/{juryCSS}")
    public ResponseEntity<JuryDTO> createJury(@PathVariable Integer juryLD, @PathVariable Integer juryCSS) {
        logger.info("Process request : Put jury");
        try {
            JuryDTO jury = juryService.addJury(juryLD, juryCSS);
            return new ResponseEntity<>(jury, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch(e.getMessage()){
                case CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                case CustomRuntimeException.TEACHING_STAFF_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.JURY_ALREADY_EXISTS:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @PutMapping("/addJuryMember")
    public ResponseEntity<TeachingStaffDTO> addJuryMemberRole(@RequestBody TeachingStaffDTO teachingStaffDTO) {
        logger.info("Process request : Put jury member role");
        try {
            TeachingStaffDTO updatedTeachingStaff = juryService.addJuryMemberRole(teachingStaffDTO);
            return ResponseEntity.ok(updatedTeachingStaff);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                // Ajoutez ici les cas spécifiques avec les réponses HTTP appropriées

                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
    
    @GetMapping("/{idJury}")
    public ResponseEntity<JuryDTO> getJury(@PathVariable Integer idJury) {
        logger.info("Process request : Get jury with id {}", idJury);
        try {
            JuryDTO jury = juryService.getJury(idJury);
            return new ResponseEntity<>(jury, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.JURY_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<JuryDTO>> getAllJuries() {
        logger.info("Process request : Get all juries");
        try {
            List<JuryDTO> juries = juryService.getAllJuries();
            return new ResponseEntity<>(juries, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting all juries", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
