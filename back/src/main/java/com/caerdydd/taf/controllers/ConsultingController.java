package com.caerdydd.taf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private ConsultingService consultingService;

    @PostMapping("/upload")
    public ResponseEntity<List<ConsultingDTO>> uploadConsulting(@RequestParam("file") MultipartFile file) throws CustomRuntimeException {
        List<ConsultingDTO> savedConsultingDTOs = consultingService.uploadConsultings(file);
        return new ResponseEntity<>(savedConsultingDTOs, HttpStatus.OK);
    }
    
}
