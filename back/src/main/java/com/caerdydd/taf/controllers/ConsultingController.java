package com.caerdydd.taf.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ConsultingService;

@RestController
@RequestMapping("api/consulting")
public class ConsultingController {

    private static final Logger logger = LogManager.getLogger(ConsultingController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    private ConsultingService consultingService;

    @GetMapping("")
    public ResponseEntity<List<PlannedTimingConsultingDTO>> getAllConsultings() {
        logger.info("Process request : Get all consultings");
        try {
            List<PlannedTimingConsultingDTO> plannedTimingConsultingDTOs = consultingService.listAllPlannedTimingConsultings();
            return new ResponseEntity<>(plannedTimingConsultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PutMapping("")
    public ResponseEntity<List<PlannedTimingConsultingDTO>> uploadConsulting(@RequestParam("file") MultipartFile file) {
        logger.info("Process request : Upload consulting");
        try {
            List<PlannedTimingConsultingDTO> savedplannedTimingConsultingDTOs = consultingService.uploadPlannedTimingConsultings(file);
            return new ResponseEntity<>(savedplannedTimingConsultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException | IOException e) {
            logger.warn(e.getMessage());
            if (e.getMessage().equals(CustomRuntimeException.FILE_IS_EMPTY)) {
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            if (e.getMessage().equals(CustomRuntimeException.INCORRECT_FILE_FORMAT)) {
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        
    }

    @PostMapping("/availability")
    public ResponseEntity<PlannedTimingAvailabilityDTO> updateAvailability(@RequestBody PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO) {
        logger.info("Process request : Update availability");
        try {
            PlannedTimingAvailabilityDTO savedPlannedTimingAvailabilityDTO = consultingService.updatePlannedTimingAvailability(plannedTimingAvailabilityDTO);
            return new ResponseEntity<>(savedPlannedTimingAvailabilityDTO, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
    
}
