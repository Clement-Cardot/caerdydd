package com.caerdydd.taf.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.entities.RoleEntity;
import com.caerdydd.taf.repositories.RoleRepository;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RoleDTO> listAllRoles() {
        return roleRepository.findAll().stream()
        .map(role -> modelMapper.map(role, RoleDTO.class))
        .collect(Collectors.toList()) ;
    }

    public void saveRole(RoleDTO role) {
        RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
        roleRepository.save(roleEntity);
    }
    
}
