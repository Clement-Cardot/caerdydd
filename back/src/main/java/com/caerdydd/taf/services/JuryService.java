package com.caerdydd.taf.services;

import java.util.Optional;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
import com.caerdydd.taf.services.rules.JuryServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;
@Service
@Transactional
public class JuryService {
    private static final Logger logger = LogManager.getLogger(JuryService.class);

    @Autowired
    private JuryRepository juryRepository;

    @Autowired 
    private TeachingStaffService teachingStaffService;

    @Autowired
    private UserService userService;

    @Autowired 
    private RoleService roleService;

    @Autowired
    private JuryServiceRules juryServiceRules;

    @Autowired
    private UserServiceRules userServiceRules;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;

    public JuryDTO findJuryByTs1AndTs2(TeachingStaffDTO ts12, TeachingStaffDTO ts22) throws CustomRuntimeException {

        TeachingStaffEntity ts1 = modelMapper.map(ts12, TeachingStaffEntity.class);
        TeachingStaffEntity ts2 = modelMapper.map(ts22, TeachingStaffEntity.class);

        Optional<JuryEntity> optionalJury1 = Optional.empty();
        Optional<JuryEntity> optionalJury2 = Optional.empty();
        try {
            optionalJury1 = juryRepository.findByTs1AndTs2(ts1, ts2);
            optionalJury2 = juryRepository.findByTs1AndTs2(ts2, ts1);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        if(!optionalJury1.isPresent() && !optionalJury2.isPresent()){
            throw new CustomRuntimeException(CustomRuntimeException.JURY_NOT_FOUND);
        }
        if(optionalJury1.isPresent()){
            return modelMapper.map(optionalJury1.get(), JuryDTO.class);
        }
        return modelMapper.map(optionalJury2.get(), JuryDTO.class);
    }

    public void checkJuryExists(Integer idTs1, Integer idTs2) throws CustomRuntimeException {
        TeachingStaffDTO ts1 = teachingStaffService.getTeachingStaffById(idTs1);
        TeachingStaffDTO ts2 = teachingStaffService.getTeachingStaffById(idTs2);

        try {
            findJuryByTs1AndTs2(ts1, ts2);
            throw new CustomRuntimeException(CustomRuntimeException.JURY_ALREADY_EXISTS);
        } catch (CustomRuntimeException e) {
            if (e.getMessage().equals(CustomRuntimeException.JURY_NOT_FOUND)) {
                return;
            }
            throw e;
        } 
        
    }
    
    public JuryDTO addJury(Integer idJuryMemberDev, Integer idJuryMemberArchi) throws CustomRuntimeException{
        userServiceRules.checkCurrentUserRole(RoleDTO.PLANNING_ROLE);

        juryServiceRules.checkDifferentTeachingStaff(idJuryMemberDev, idJuryMemberArchi);
        checkJuryExists(idJuryMemberDev, idJuryMemberArchi);

        TeachingStaffDTO juryMemberDev = teachingStaffService.getTeachingStaffById(idJuryMemberDev);
        TeachingStaffDTO juryMemberArchi = teachingStaffService.getTeachingStaffById(idJuryMemberArchi);

        TeachingStaffDTO updatedjuryMemberDev = this.addJuryMemberRole(juryMemberArchi);
        TeachingStaffDTO updatedjuryMemberArchi = this.addJuryMemberRole(juryMemberDev);

        JuryDTO juryDTO = new JuryDTO(updatedjuryMemberDev, updatedjuryMemberArchi);
        return updateJury(juryDTO);
    }

    public JuryDTO updateJury(JuryDTO juryDTO) throws CustomRuntimeException {
        JuryEntity juryEntity = modelMapper.map(juryDTO, JuryEntity.class);
        
        JuryEntity response = null;
        try {
            response = juryRepository.save(juryEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, JuryDTO.class);
    }

    public TeachingStaffDTO addJuryMemberRole(TeachingStaffDTO teachingStaffDTO) throws CustomRuntimeException {
        UserDTO user = teachingStaffDTO.getUser();
        // Vérifier que l'utilisateur actuel a le rôle "PLANNING_ROLE"
        userServiceRules.checkCurrentUserRole(RoleDTO.PLANNING_ROLE);
    
        // Vérifier si le rôle JURY_MEMBER_ROLE existe déjà
        if (teachingStaffDTO.getUser().getRoles().stream().anyMatch(role -> role.getRole().equals("JURY_MEMBER_ROLE"))) {
            // Le rôle JURY_MEMBER_ROLE existe déjà, pas besoin de l'ajouter à nouveau
            return teachingStaffDTO;
        }
    
        // Créer et sauvegarder le nouveau rôle JURY_MEMBER_ROLE
        logger.info("Create role of User {}: JURY_MEMBER_ROLE", user.getId());
        RoleDTO juryMemberRole = new RoleDTO();
        juryMemberRole.setRole("JURY_MEMBER_ROLE");
        juryMemberRole.setUser(user);
        teachingStaffDTO.getUser().getRoles().add(juryMemberRole);
        logger.info("Save modifications ...");
        userService.updateUser(user);
        logger.info("Modifications saved !");
        return this.teachingStaffService.getTeachingStaffById(user.getId());
    }

}