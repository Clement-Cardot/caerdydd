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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.PresentationService;

@RestController
@RequestMapping("api/presentation")
public class PresentationController {


    private static final Logger logger = LogManager.getLogger(PresentationController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    PresentationService presentationService;
    

    @PutMapping("")
    public ResponseEntity<PresentationDTO> createPresentation(@RequestBody PresentationDTO presentation) {
        logger.info("Process request : Create presentation");
        try {
            PresentationDTO savedPresentationDTO = presentationService.createPresentation(presentation);
            return new ResponseEntity<>(savedPresentationDTO, HttpStatus.CREATED);
        } catch (CustomRuntimeException e) {
            logger.warn(e.getMessage());
            switch (e.getMessage()) {
                case CustomRuntimeException.JURY_NOT_FOUND:
                case CustomRuntimeException.PROJECT_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.TEACHING_STAFF_NOT_AVAILABLE:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                case CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case CustomRuntimeException.PRESENTATION_END_BEFORE_BEGIN:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        } 
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresentationDTO> getPresentationById(@PathVariable("id") Integer presentationId) {
        try {
            PresentationDTO presentationDTO = presentationService.getPresentationById(presentationId);
            return new ResponseEntity<>(presentationDTO, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.PRESENTATION_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PresentationDTO>> getTeamPresentations(@PathVariable("teamId") Integer teamId) {
        try {
            List<PresentationDTO> presentations = presentationService.getTeamPresentations(teamId);
            return new ResponseEntity<>(presentations, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.TEAM_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/teachingStaff/{staffId}")
    public ResponseEntity<List<PresentationDTO>> getTeachingStaffPresentations(@PathVariable Integer staffId) {
        try {
            List<PresentationDTO> presentations = presentationService.getTeachingStaffPresentations(staffId);
            return ResponseEntity.ok(presentations);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.TEACHING_STAFF_NOT_FOUND)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PresentationDTO>> listAllPresentations() {
        logger.info("Process request : List all presentations");
        try {
            List<PresentationDTO> presentations = presentationService.listAllPresentations();
            return new ResponseEntity<>(presentations, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.SERVICE_ERROR)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
