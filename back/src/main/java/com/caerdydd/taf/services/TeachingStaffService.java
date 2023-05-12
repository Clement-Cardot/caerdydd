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

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
import com.caerdydd.taf.models.entities.TeachingStaffEntity;
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

  @Autowired
  private UserService userService;
  
  public List<TeachingStaffDTO> listAllTeachingStaff() throws CustomRuntimeException {
      try {
          return teachingStaffRepository.findAll().stream()
          .map(teachingStaff -> modelMapper.map(teachingStaff, TeachingStaffDTO.class))
          .collect(Collectors.toList()) ;
      } catch (Exception e) {
          throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
      }
  }

  public TeachingStaffDTO getTeachingStaffById(Integer idUser) throws CustomRuntimeException  {
    Optional<TeachingStaffEntity> optionalTeachingStaff;
    try {
      optionalTeachingStaff = teachingStaffRepository.findById(idUser);
    } catch(Exception e) {
      throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
    }
    if(optionalTeachingStaff.isEmpty()){
      throw new CustomRuntimeException(CustomRuntimeException.TEACHINGSTAFF_NOT_FOUND);
    }
    return modelMapper.map(
      optionalTeachingStaff.get(),
     TeachingStaffDTO.class
     );
  }

  public TeachingStaffDTO saveTeachingStaff(TeachingStaffDTO teachingStaff) throws CustomRuntimeException {
    TeachingStaffEntity teachingStaffEntity = modelMapper.map(
      teachingStaff, TeachingStaffEntity.class);

    if(teachingStaffEntity.getUser().getId() != null){
      throw new CustomRuntimeException(CustomRuntimeException.TEACHINGSTAFF_ID_SHOULD_BE_NULL);
    }

    TeachingStaffEntity response = null;
    try{
        response = teachingStaffRepository.save(teachingStaffEntity);
    } catch(Exception e) {
        throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
    }

    return modelMapper.map(response, TeachingStaffDTO.class);
  }

 public TeachingStaffDTO updateTeachingStaff(TeachingStaffDTO teachingStaff) throws CustomRuntimeException {
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
