package com.caerdydd.taf.services;

import java.util.Optional;

import java.util.Optional;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
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
    @Autowired
    private JuryRepository juryRepository;

    @Autowired 
    private TeachingStaffService teachingStaffService;

    @Autowired 
    private RoleService roleService;

    @Autowired
    private JuryServiceRules juryServiceRules;

    @Autowired
    private UserServiceRules userServiceRules;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeachingStaffService teachingStaffService;

    @Autowired
    UserServiceRules userServiceRules;
    
    public JuryDTO addJury(Integer idJuryMemberDev, Integer idJuryMemberArchi) throws CustomRuntimeException{
        userServiceRules.checkCurrentUserRole(RoleDTO.PLANNING_ROLE);

        // TODO verifier que un user est LD et l'autre CSS -> teachingStaffRules

        juryServiceRules.checkDifferentTeachingStaff(idJuryMemberDev, idJuryMemberArchi);
        checkJuryExists(idJuryMemberDev, idJuryMemberArchi);

        TeachingStaffDTO juryMemberDev = teachingStaffService.getTeachingStaffById(idJuryMemberDev);
        TeachingStaffDTO juryMemberArchi = teachingStaffService.getTeachingStaffById(idJuryMemberArchi);

        roleService.assignRoleToUser(idJuryMemberDev, "JURY_MEMBER_ROLE");
        roleService.assignRoleToUser(idJuryMemberArchi, "JURY_MEMBER_ROLE");

        JuryDTO juryDTO = new JuryDTO(juryMemberDev, juryMemberArchi);
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
}
