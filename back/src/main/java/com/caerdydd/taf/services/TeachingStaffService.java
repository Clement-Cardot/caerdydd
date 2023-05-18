package com.caerdydd.taf.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.UserServiceRules;


@Service
@Transactional
public class TeachingStaffService {
  private static final Logger logger = LogManager.getLogger(TeachingStaffService.class);

  @Autowired
  private TeachingStaffRepository teachingStaffRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  UserServiceRules userServiceRules;
  
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

  public TeachingStaffDTO getTeachingStaffById(Integer id) throws CustomRuntimeException {
      Optional<TeachingStaffEntity> optionalTeachingStaff;
      try {
          optionalTeachingStaff = teachingStaffRepository.findById(id);
      } catch (Exception e) {
          throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
      }
      
      if (optionalTeachingStaff.isEmpty()) {
          throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_FOUND);
      }
      return modelMapper.map(optionalTeachingStaff.get(), TeachingStaffDTO.class);
  } 

  public TeachingStaffDTO saveTeachingStaff(TeachingStaffDTO teachingStaff) {    
    TeachingStaffEntity teachingStaffEntity = modelMapper.map(
      teachingStaff, TeachingStaffEntity.class);

      TeachingStaffEntity response = teachingStaffRepository.save(teachingStaffEntity);

    return modelMapper.map(response, TeachingStaffDTO.class);
  }

  public TeachingStaffDTO updateTeachingStaff(TeachingStaffDTO teachingStaff) throws CustomRuntimeException {
    // Verify if the current user is a team member
    userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");
  
    TeachingStaffEntity teachingStaffEntity = modelMapper.map(teachingStaff, TeachingStaffEntity.class);
    
    Optional<TeachingStaffEntity> optionalTeachingStaff = teachingStaffRepository.findById(teachingStaffEntity.getUser().getId());
    if (optionalTeachingStaff.isEmpty()){
        throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
    }

    TeachingStaffEntity response = null;
    try {
        response = teachingStaffRepository.save(teachingStaffEntity);
    } catch (Exception e) {
        throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
    }

    return modelMapper.map(response, TeachingStaffDTO.class);
  }
    
}
