package com.caerdydd.taf.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.JuryDTO;
import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.JuryEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired 
    private UserService userService;

    //@Autowired
    //private JuryServiceRules juryServiceRules;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JuryServiceRules juryServiceRules;

    @Autowired
    SecurityConfig securityConfig;
    
    public JuryDTO addJuryMembers(Integer idJuryMemberDev, Integer idJuryMemberArchi) throws CustomRuntimeException{
        
        // TODO remplacer ça par UserRules
        if(securityConfig.getCurrentUser().getRoles().stream().noneMatch(role -> role.getRole().equals(RoleDTO.PLANNING_ROLE))){
            logger.warn("ILLEGAL API USE : Current user is not a option leader");
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT);
        }

        // TODO verifier que un user est LD et l'autre CSS -> teachingStaffRules

        juryServiceRules.checkDifferentTeachingStaff(idJuryMemberDev, idJuryMemberArchi);
        juryServiceRules.checkJuryExists(idJuryMemberDev, idJuryMemberArchi);

        UserDTO juryMemberDev = userService.getUserById(idJuryMemberDev);
        UserDTO juryMemberArchi = userService.getUserById(idJuryMemberArchi);

        JuryDTO juryDTO = new JuryDTO(juryMemberDev, juryMemberArchi);
        return updateJury(juryDTO);
    }

    public JuryEntity getJuryByIdTs1AdnIdTs2(Integer idTs1, Integer idTs2){
        Optional<UserEntity> optTs1 = userRepository.findById(idTs1);
        Optional<UserEntity> optTs2 = userRepository.findById(idTs2);
        
        UserEntity ts1 = optTs1.orElseThrow(() -> new IllegalArgumentException("UserEntity not found for idTs1: " + idTs1));
        UserEntity ts2 = optTs2.orElseThrow(() -> new IllegalArgumentException("UserEntity not found for idTs2: " + idTs2));

        List<JuryEntity> result = juryRepository.findByTs1AndTs2(ts1, ts2);

        return result.get(0);
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
