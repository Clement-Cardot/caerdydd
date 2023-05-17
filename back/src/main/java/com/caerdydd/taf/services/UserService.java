package com.caerdydd.taf.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private static final String USER_NOT_FOUND = "User not found";

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
            logger.error("Error listing all users:", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public UserDTO getUserById(Integer id) throws CustomRuntimeException {
        Optional<UserEntity> optionalUser = Optional.empty();
        try {
            optionalUser = userRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error getting user by id:", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if (optionalUser.isEmpty()) {
            logger.error(USER_NOT_FOUND);
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO getUserByLogin(String login) throws CustomRuntimeException {
        Optional<UserEntity> optionalUser = Optional.empty();
        try{
            optionalUser = userRepository.findByLogin(login);
        } catch (Exception e) {
            logger.error("Error getting user by login:", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if (optionalUser.isEmpty()) {
            logger.error(USER_NOT_FOUND);
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO saveUser(UserDTO user) throws CustomRuntimeException {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        
        if (userEntity.getId() != null){
            logger.error("User id should be null");
            throw new CustomRuntimeException(CustomRuntimeException.USER_ID_SHOULD_BE_NULL);
        }

        UserEntity response = null;
        try {
            response = userRepository.save(userEntity);
        } catch (Exception e) {
            logger.error("Error saving user:", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, UserDTO.class);
    }

    public UserDTO updateUser(UserDTO user) throws CustomRuntimeException {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        
        Optional<UserEntity> optionalUser = userRepository.findById(userEntity.getId());
        if (optionalUser.isEmpty()){
            logger.error(USER_NOT_FOUND);
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }

        UserEntity response = null;
        try {
            response = userRepository.save(userEntity);
        } catch (Exception e) {
            logger.error("Error updating user:", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, UserDTO.class);
    }

    public void deleteUserById(Integer id) throws CustomRuntimeException {
        userRepository.deleteById(id);
    }

}
