package com.caerdydd.taf.services;


import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.JuryDTO;
import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.JuryEntity;
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
    private UserService userService;

    @Autowired
    private JuryServiceRules juryServiceRules;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceRules userServiceRules;

    @Autowired
    SecurityConfig securityConfig;
    
    public JuryDTO addJuryMembers(Integer idJuryMemberDev, Integer idJuryMemberArchi) throws CustomRuntimeException{
        userServiceRules.checkCurrentUserRole(RoleDTO.PLANNING_ROLE);

        // TODO verifier que un user est LD et l'autre CSS -> teachingStaffRules

        juryServiceRules.checkDifferentTeachingStaff(idJuryMemberDev, idJuryMemberArchi);
        juryServiceRules.checkJuryExists(idJuryMemberDev, idJuryMemberArchi);

        UserDTO juryMemberDev = userService.getUserById(idJuryMemberDev);
        UserDTO juryMemberArchi = userService.getUserById(idJuryMemberArchi);

        JuryDTO juryDTO = new JuryDTO(juryMemberDev, juryMemberArchi);
        return updateJury(juryDTO);
    }

    public JuryDTO updateJury(JuryDTO juryDTO) throws CustomRuntimeException {
        JuryEntity juryEntity = modelMapper.map(juryDTO, JuryEntity.class);
        
        // TODO verifier que un user est LD et l'autre CSS -> userRules
        // TODO verifier que le jury n'existe pas deja -> juryRules
        /* TODO verifier les 2 users 
        Optional<TeamMemberEntity> optionalUser = juryRepository.findById(teamMemberEntity.getIdUser());
        if (optionalUser.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }*/

        JuryEntity response = null;
        try {
            response = juryRepository.save(juryEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, JuryDTO.class);
    }
}
