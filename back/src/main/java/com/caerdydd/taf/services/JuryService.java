package com.caerdydd.taf.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@Service
@Transactional
public class JuryService {
    private static final Logger logger = LogManager.getLogger(JuryService.class);

    @Autowired
    private JuryRepository juryRepository;

    @Autowired 
    private UserService userService;

    //@Autowired
    //private JuryServiceRules juryServiceRules;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;
    
    public JuryDTO addJuryMembers(Integer idJuryMemberLD, Integer idJuryMemberCSS) throws CustomRuntimeException{
        
        if(securityConfig.getCurrentUser().getRoles().stream().noneMatch(role -> role.getRole().equals(RoleDTO.PLANNING_ROLE))){
            logger.warn("ILLEGAL API USE : Current user is not a option leader");
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT);
        }

        UserDTO juryMemberLD = userService.getUserById(idJuryMemberLD);
        UserDTO juryMemberCSS = userService.getUserById(idJuryMemberCSS);

        JuryDTO juryDTO = new JuryDTO(juryMemberLD, juryMemberCSS);
        return updateJury(juryDTO);
    }

    public JuryDTO updateJury(JuryDTO juryDTO) throws CustomRuntimeException {
        JuryEntity juryEntity = modelMapper.map(juryDTO, JuryEntity.class);
        
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
