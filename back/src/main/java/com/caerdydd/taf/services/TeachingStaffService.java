package com.caerdydd.taf.services;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
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

  public void defineSpecialty(Integer idUser) throws CustomRuntimeException {
    // Check if the user exists
    UserDTO user;
    try {
        user = userService.getUserById(idUser);
    } catch (NoSuchElementException e) {
        logger.warn("User {} not found", idUser);
        throw new CustomRuntimeException("User not found");
    }

    // Check if the current user is the same as the user to update
    if(Boolean.FALSE.equals(securityConfig.checkCurrentUser(idUser))){
        logger.warn("ILLEGAL API USE : Current teaching staff : {} tried to define a specialty for user {}", securityConfig.getCurrentUser().getId(), idUser);
        throw new CustomRuntimeException("Can't define a specialty for another user");
    }

    // Check if the user already has a specialty
    if (userService.getUserById(idUser).getSpeciality() != null) {
        String specialtyAlreadyDefined = userService.getUserById(idUser).getSpeciality();
        logger.warn("ILLEGAL API USE : Current teaching staff : {} tried to define a specialty but already has {} specialty.", idUser, specialtyAlreadyDefined);
        throw new CustomRuntimeException("User already has specialty.");
    }

    // If everythings OK : create the user role "team_member" and create a new team member entity
    try {
    logger.info("Create specialty of User Teaching Staff {} : is_infrastructure_specialist", idUser);
    TeachingStaffDTO newSpecialty = new TeachingStaffDTO();
    newSpecialty.setIsInfrastructureSpecialist(1);
    user.setTeachingStaff(newSpecialty);
    } catch(NoSuchElementException e){
      logger.warn("Wrong specialty selected : not infrastructure");
      throw new CustomRuntimeException("Wrong specialty selected : not infrastructure");
    }

    try {
      logger.info("Create specialty of User Teaching Staff {} : is_development_specialist", idUser);
      TeachingStaffDTO newSpecialty = new TeachingStaffDTO();
      newSpecialty.setIsDevelopmentSpecialist(1);
      user.setTeachingStaff(newSpecialty);
    } catch(NoSuchElementException e){
      logger.warn("Wrong specialty selected : not development");
      throw new CustomRuntimeException("Wrong specialty selected : not development");
    }
    
    try {
      logger.info("Create specialty of User Teaching Staff {} : is_modeling_specialist", idUser);
      TeachingStaffDTO newSpecialty = new TeachingStaffDTO();
      newSpecialty.setIsModelingSpecialist(1);
      user.setTeachingStaff(newSpecialty);
    } catch(NoSuchElementException e){
      logger.warn("Wrong specialty selected : not modeling");
      throw new CustomRuntimeException("Wrong specialty selected : not modeling");
    }

      logger.info("Save modifications ...");
      userService.saveUser(user);
      logger.info("Modifications saved !");
  }
}
