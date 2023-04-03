package com.caerdydd.taf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.UserEntity;
import com.caerdydd.taf.repository.UserRepository;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> listAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        // Optional<UserEntity> user = userRepository.findById(id);
        // if (user.isEmpty()) {
        //     throw new NotFoundException();
        // }
        // return user.get();
        return userRepository.findById(id).get();
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
