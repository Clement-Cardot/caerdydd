package com.caerdydd.taf.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;

@RestController
@RequestMapping("/api/teaching_staff")
public class TeachingStaffController {

    private static final Logger logger = LogManager.getLogger(TeachingStaffController.class);
    
    @Autowired
    private TeachingStaffService teachingStaffService;

    @GetMapping("")
    public ResponseEntity<List<TeachingStaffDTO>> getAllTeachingStaff() {
      logger.info("Process request : Get all teaching staff");
        try {
            List<TeachingStaffDTO> teachingStaffs = teachingStaffService.listAllTeachingStaff();
            return new ResponseEntity<>(teachingStaffs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.error("Unexpected Exception : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        case CustomRuntimeException.USER_NOT_FOUND:
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        default:
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
  }

}
