package com.caerdydd.taf.services;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class TeachingStaffService {
    private static final Logger logger = LogManager.getLogger(TeachingStaffService.class);

    @Autowired
    private TeachingStaffRepository teachingStaffRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    public List<TeachingStaffDTO> listAllTeachingStaff() throws CustomRuntimeException {
        try {
            return teachingStaffRepository.findAll().stream()
            .map(teachingStaff -> modelMapper.map(teachingStaff, TeachingStaffDTO.class))
            .collect(Collectors.toList()) ;
        } catch (Exception e) {
            logger.error("Error listing all teaching staff:", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }
}
