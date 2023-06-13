package com.caerdydd.taf.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ConsultingService;

@RestController
@RequestMapping("api/consulting")
public class ConsultingController {

    private static final Logger logger = LogManager.getLogger(ConsultingController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    private ConsultingService consultingService;

    @GetMapping("/plannedTiming")
    public ResponseEntity<List<PlannedTimingConsultingDTO>> getAllPlannedTimingConsultings() {
        logger.info("Process request : Get all consultings");
        try {
            List<PlannedTimingConsultingDTO> plannedTimingConsultingDTOs = consultingService.listAllPlannedTimingConsultings();
            return new ResponseEntity<>(plannedTimingConsultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/TeachingStaff")
    public ResponseEntity<List<ConsultingDTO>> getConsultingsForCurrentTeachingStaff() {
        logger.info("Process request : Get all consultings for current teaching staff");
        try {
            List<ConsultingDTO> consultingDTOs = consultingService.getConsultingsForCurrentTeachingStaff();
            return new ResponseEntity<>(consultingDTOs, HttpStatus.OK);
        }
        catch (CustomRuntimeException e) {
            if(e.getMessage().equals(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if(e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @GetMapping("/team/{idTeam}")
    public ResponseEntity<List<ConsultingDTO>> getConsultingsForATeam(@PathVariable Integer idTeam) {
        logger.info("Process request : Get all consultings for a team");
        try {
            List<ConsultingDTO> consultingDTOs = consultingService.getConsultingsForATeam(idTeam);
            logger.info("Consulting for a team" + consultingDTOs);
            return new ResponseEntity<>(consultingDTOs, HttpStatus.OK);
        }
        catch (CustomRuntimeException e) {
            switch(e.getMessage()) {
                case CustomRuntimeException.USER_IS_NOT_AUTHORIZED:
                case CustomRuntimeException.USER_NOT_IN_ASSOCIATED_TEAM:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @PutMapping("/plannedTiming")
    public ResponseEntity<List<PlannedTimingConsultingDTO>> uploadPlannedTimingConsultings(@RequestParam("file") MultipartFile file) {
        logger.info("Process request : Upload consulting");
        try {
            List<PlannedTimingConsultingDTO> savedplannedTimingConsultingDTOs = consultingService.uploadPlannedTimingConsultings(file);
            return new ResponseEntity<>(savedplannedTimingConsultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException | IOException e) {
            if (e.getMessage().equals(CustomRuntimeException.FILE_IS_EMPTY)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            if (e.getMessage().equals(CustomRuntimeException.INCORRECT_FILE_FORMAT)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        
    }

    @PutMapping("/notes")
    public ResponseEntity<ConsultingDTO> setNotesConsulting(@RequestParam("id") String idConsulting, @RequestParam("notes") String notes) {
        logger.info("Process request : Set notes consulting");
        try {
            ConsultingDTO savedConsultingDTO = consultingService.setNotesConsulting(idConsulting, notes);
            return new ResponseEntity<>(savedConsultingDTO, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF:
                case CustomRuntimeException.USER_IS_NOT_OWNER_OF_CONSULTING:
                case CustomRuntimeException.CONSULTING_NOT_FINISHED:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @PostMapping("/availability")
    public ResponseEntity<PlannedTimingAvailabilityDTO> updateAvailability(@RequestBody PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO) {
        logger.info("Process request : Update availability");
        try {
            PlannedTimingAvailabilityDTO savedPlannedTimingAvailabilityDTO = consultingService.updatePlannedTimingAvailability(plannedTimingAvailabilityDTO);
            return new ResponseEntity<>(savedPlannedTimingAvailabilityDTO, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF:
                case CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY:
                case CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST:                
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN:
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
    
}
