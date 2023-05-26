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

    @Autowired
    UserServiceRules userServiceRules;
    
    public JuryDTO addJury(Integer idJuryMemberDev, Integer idJuryMemberArchi) throws CustomRuntimeException{
        userServiceRules.checkCurrentUserRole(RoleDTO.PLANNING_ROLE);

        // TODO CHECK SPECIALITY

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