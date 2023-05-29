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
import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@RestController
@RequestMapping("/api/juries")
public class JuryController {

    private static final Logger logger = LogManager.getLogger(JuryController.class);

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
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
    
    @GetMapping("/{idJury}")
    public ResponseEntity<JuryDTO> getJury(@PathVariable Integer idJury) {
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
    try {
        List<JuryDTO> juries = juryService.getAllJuries();
        return new ResponseEntity<>(juries, HttpStatus.OK);
    } catch (Exception e) {
        logger.error("Error while getting all juries", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
