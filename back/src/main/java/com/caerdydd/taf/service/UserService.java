package com.caerdydd.taf.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public UserEntity getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
