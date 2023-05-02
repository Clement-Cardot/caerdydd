package com.caerdydd.taf.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.services.JuryService;
import com.caerdydd.taf.models.dto.JuryDTO;
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
            JuryDTO jury = juryService.addJuryMembers(juryLD, juryCSS);
            return new ResponseEntity<>(jury, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
