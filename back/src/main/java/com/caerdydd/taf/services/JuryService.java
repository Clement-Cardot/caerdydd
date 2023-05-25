package com.caerdydd.taf.services;

import java.util.Optional;

import java.util.Optional;

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

        TeachingStaffDTO juryMemberDev = teachingStaffService.getTeachingStaffById(idJuryMemberDev);
        TeachingStaffDTO juryMemberArchi = teachingStaffService.getTeachingStaffById(idJuryMemberArchi);

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
