package com.caerdydd.taf.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;

@RestController
@RequestMapping("/api/teachingStaffSpe")
public class TeachingStaffController {

    private static final Logger logger = LogManager.getLogger(TeachingStaffController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";
    
    @Autowired
    private TeachingStaffService teachingStaffService;

    @GetMapping("")
    public ResponseEntity<List<TeachingStaffDTO>> getAllTeachingStaff() {
      logger.info("Process request : Get all teaching staff");
        try {
            List<TeachingStaffDTO> teachingStaffs = teachingStaffService.listAllTeachingStaff();
            return new ResponseEntity<>(teachingStaffs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/modifySpeciality/{teachingStaffId}")
    public ResponseEntity<TeachingStaffDTO> getTeachingStaffById(@PathVariable Integer teachingStaffId) {
      logger.info("Process request : Get teachingStaff by id : {}", teachingStaffId);
      try {
            TeachingStaffDTO teachingStaff = teachingStaffService.getTeachingStaffById(teachingStaffId);
            return new ResponseEntity<>(teachingStaff, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
          if (e.getMessage().equals(CustomRuntimeException.TEACHINGSTAFF_NOT_FOUND)) {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
          if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
              return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
          }
          logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
          return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
      }
    }


    @PostMapping("/modifySpeciality")
    public ResponseEntity<TeachingStaffDTO> submitSpeciality(@RequestBody TeachingStaffDTO teachingStaffDTO ) {
        Integer teachingStaffId = teachingStaffDTO.getUser().getId();
        logger.info("Request submit speciality with id {}", teachingStaffId);
        try {
        TeachingStaffDTO updatedTeachingStaff = teachingStaffService.updateTeachingStaff(teachingStaffDTO);
        logger.info("Success submit speciality with id {}", teachingStaffId);
        return new ResponseEntity<>(updatedTeachingStaff, HttpStatus.OK);
      }  catch (CustomRuntimeException e) {
        switch (e.getMessage()) {
        case CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER:
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        case CustomRuntimeException.TEACHINGSTAFF_NOT_FOUND:
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        default: return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
    }
}

