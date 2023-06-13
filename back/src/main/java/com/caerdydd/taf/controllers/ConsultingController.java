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

    @GetMapping("/plannedAvailability")
    public ResponseEntity<List<PlannedTimingAvailabilityDTO>> getAllPlannedTimingAvailabilities() {
        logger.info("Process request : Get all planned timing availabilities");
        try {
            List<PlannedTimingAvailabilityDTO> plannedTimingAvailabilitiesDTOs = consultingService.listAllPlannedTimingAvailabilities();
            logger.info("Process request : Get all planned timing availabilities DONE");
            return new ResponseEntity<>(plannedTimingAvailabilitiesDTOs, HttpStatus.OK);
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
    @GetMapping("/consultings")
    public ResponseEntity<List<ConsultingDTO>> getAllConsultings() {
        logger.info("Process request : Get all planned timing consultings");
        try {
            List<ConsultingDTO> plannedTimingConsultingDTOs = consultingService.listAllConsultings();
            return new ResponseEntity<>(plannedTimingConsultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                logger.warn(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
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

    @GetMapping("/teachingStaffConsultingInfra")
    public ResponseEntity<List<ConsultingDTO>> getConsultingsBySpecialityInfra() {
        logger.info("Process request : Get all consultings for speciality Infra");
        try {
            List<ConsultingDTO> consultingInfraDTOs = consultingService.getConsultingsBySpecialityInfra();
            logger.info("Process request : Get all consultings for speciality Infra DONE");
            return new ResponseEntity<>(consultingInfraDTOs, HttpStatus.OK);
        }
        catch (CustomRuntimeException e) {
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

    @GetMapping("/teachingStaffConsultingDevelopment")
    public ResponseEntity<List<ConsultingDTO>> getConsultingsBySpecialityDev() {
        logger.info("Process request : Get all consultings for speciality Dev");
        try {
            List<ConsultingDTO> consultingDevDTOs = consultingService.getConsultingsBySpecialityDevelopment();
            logger.info("Process request : Get all consultings for speciality Dev DONE");
            return new ResponseEntity<>(consultingDevDTOs, HttpStatus.OK);
        }
        catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.CONSULTING_NOT_FOUND:
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

    @GetMapping("/teachingStaffConsultingModeling")
    public ResponseEntity<List<ConsultingDTO>> getConsultingsBySpecialityModel() {
        logger.info("Process request : Get all consultings for speciality Modeling");
        try {
            List<ConsultingDTO> consultingModelDTOs = consultingService.getConsultingsBySpecialityModeling();
            logger.info("Process request : Get all consultings for Modeling DONE");
            return new ResponseEntity<>(consultingModelDTOs, HttpStatus.OK);
        }
        catch (CustomRuntimeException e) {
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

    @PostMapping("/consulting")
    public ResponseEntity<ConsultingDTO> updateConsulting(@RequestBody ConsultingDTO consultingDTO) {
        logger.info("Process request : Update consulting");
        try {
            ConsultingDTO savedConsultingDTO = consultingService.updateConsulting(consultingDTO);
            logger.info("Process request : Update consulting DONE");
            return new ResponseEntity<>(savedConsultingDTO, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.CONSULTING_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF:
                case CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY:
                case CustomRuntimeException.CONSULTING_IS_IN_PAST:                
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN:
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
    @PutMapping("/createConsulting")
    public ResponseEntity<ConsultingDTO> createConsulting(@RequestBody ConsultingDTO consultingDTO) {
        logger.info("Process request : Create consulting");
        try {
            ConsultingDTO newConsulting = consultingService.createConsulting(consultingDTO);
            return new ResponseEntity<>(newConsulting, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            switch (e.getMessage()) {
                case CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.USER_IS_NOT_A_TEAM_MEMBER:
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
