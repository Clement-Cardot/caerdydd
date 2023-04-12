package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;

@RestController
@RequestMapping("/api/users/teaching_staff")
public class TeachingStaffController {

  private static final Logger logger = LogManager.getLogger(TeamController.class);

  @Autowired
  private TeachingStaffService teachingStaffService;


    @GetMapping("")
    public ResponseEntity<List<TeachingStaffDTO>> getAllTeachingStaff() {
      logger.info("Process request : Get all teaching staff");

        try {
            List<TeachingStaffDTO> teachingStaffs = teachingStaffService.listAllTeachingStaff();
            return new ResponseEntity<>(teachingStaffs, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            logger.error("Unexpected Exception : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<TeachingStaffDTO> submitSpecialty(@PathVariable Integer idUser) {
      logger.info("Process request : Define a specialty with user {}", idUser);
      try {
        teachingStaffService.defineSpecialty(idUser);
        return new ResponseEntity<>(HttpStatus.OK);
      }  catch (CustomRuntimeException e) {
        switch (e.getMessage()) {
        case "Can't define a specialty for an other teaching staff":
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        case "Teaching staff already has a specialty":
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        case "Teaching staff user not found":
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        default:
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
  }

}
