package com.caerdydd.taf.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.ConsultingDTO;
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
    public ResponseEntity<List<ConsultingDTO>> getAllConsultings() {
        logger.info("Process request : Get all consultings");
        try {
            List<ConsultingDTO> consultingDTOs = consultingService.listAllConsultings();
            return new ResponseEntity<>(consultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ConsultingDTO>> uploadConsulting(@RequestParam("file") MultipartFile file) {
        logger.info("Process request : Upload consulting");
        try {
            List<ConsultingDTO> savedConsultingDTOs = consultingService.uploadConsultings(file);
            return new ResponseEntity<>(savedConsultingDTOs, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.warn(e.getMessage());
            if (e.getMessage().equals(CustomRuntimeException.FILE_EXCEPTION)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } 
            if (e.getMessage().equals(CustomRuntimeException.FILE_IS_EMPTY)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            if (e.getMessage().equals(CustomRuntimeException.INCORRECT_FILE_FORMAT)) {
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        
    }
    
}
