package com.caerdydd.taf.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;

@RestController
@RequestMapping("/api/teachingStaffs")
public class TeachingStaffController {

    private static final Logger logger = LogManager.getLogger(TeachingStaffController.class);
    
    @Autowired
    private TeachingStaffService teachingStaffService;

    @GetMapping("")
    public ResponseEntity<List<TeachingStaffDTO>> list() {
        logger.info("Process request : List all teaching staffs");
        try {
            List<TeachingStaffDTO> teachingStaffs = teachingStaffService.listAllTeachingStaff();
            return new ResponseEntity<>(teachingStaffs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
