package com.caerdydd.taf.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> listAllUsers() throws CustomRuntimeException {
        try {
            return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public UserDTO getUserById(Integer id) throws CustomRuntimeException {
        Optional<UserEntity> optionalUser = Optional.empty();
        try {
            optionalUser = userRepository.findById(id);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if (optionalUser.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO getUserByLogin(String login) throws CustomRuntimeException {
        Optional<UserEntity> optionalUser = Optional.empty();
        try{
            optionalUser = userRepository.findByLogin(login);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if (optionalUser.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO saveUser(UserDTO user) throws CustomRuntimeException {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        
        Optional<UserEntity> optionalUser = userRepository.findById(userEntity.getId());
        if (optionalUser.isPresent()){
            throw new CustomRuntimeException(CustomRuntimeException.USER_ALREADY_EXISTS);
        }

        UserEntity response = null;
        try {
            response = userRepository.save(userEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, UserDTO.class);
    }

    public UserDTO updateUser(UserDTO user) throws CustomRuntimeException {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        
        Optional<UserEntity> optionalUser = userRepository.findById(userEntity.getId());
        if (optionalUser.isEmpty()){
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }

        UserEntity response = null;
        try {
            response = userRepository.save(userEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, UserDTO.class);
    }

    public void deleteUserById(Integer id) throws CustomRuntimeException {
        userRepository.deleteById(id);
    }
}
