package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
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
        } catch (NoSuchElementException e) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
    }

    public UserDTO getUserById(Integer id) throws CustomRuntimeException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO getUserByLogin(String login) throws CustomRuntimeException {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO saveUser(UserDTO user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        UserEntity response = userRepository.save(userEntity);

        return modelMapper.map(response, UserDTO.class);
    }

    public void updateUser(UserDTO userRequest) throws CustomRuntimeException {
        UserEntity user = userRepository.findById(userRequest.getId())
                                        .orElseThrow(() -> new CustomRuntimeException("User not found"));

        modelMapper.map(userRequest, user);
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
