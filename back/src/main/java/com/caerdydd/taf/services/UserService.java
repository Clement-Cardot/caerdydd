package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UserDTO> listAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) throws NoSuchElementException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException();
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public UserDTO getUserByLogin(String login) throws NoSuchElementException {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException();
        }
        return modelMapper.map(optionalUser.get(), UserDTO.class);
    }

    public void registerNewUser(UserDTO user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
    }

    public void updateUser(UserDTO userRequest) {
        UserEntity user = userRepository.findById(userRequest.getId())
                                        .orElseThrow(NoSuchElementException::new);

        modelMapper.map(userRequest, user);
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
