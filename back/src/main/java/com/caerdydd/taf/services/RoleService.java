package com.caerdydd.taf.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.entities.user.RoleEntity;
import com.caerdydd.taf.repositories.RoleRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RoleDTO> listAllRoles() throws CustomRuntimeException {
        try {
            return roleRepository.findAll().stream()
                        .map(role -> modelMapper.map(role, RoleDTO.class))
                        .collect(Collectors.toList()) ;
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public RoleDTO saveRole(RoleDTO role) {
        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);

        RoleEntity response = roleRepository.save(roleEntity);

        return modelMapper.map(response, RoleDTO.class);
    }

    public RoleDTO getRoleById(Integer id) throws CustomRuntimeException {
        try {
            RoleEntity roleEntity = roleRepository.findByIdRole(id);
            return modelMapper.map(roleEntity, RoleDTO.class);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public void deleteRole(RoleDTO role) throws CustomRuntimeException {
        try {
            roleRepository.delete(modelMapper.map(role, RoleEntity.class));
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }
    
}
