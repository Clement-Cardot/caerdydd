package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> listAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) throws NoSuchElementException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException();
        }
        return user.get();
    }

    public UserEntity getUserByLogin(String login) throws NoSuchElementException {
        Optional<UserEntity> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new NoSuchElementException();
        }
        return user.get();
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(Integer id, UserEntity userRequest) {
        UserEntity user = userRepository.findById(id)
                                        .orElseThrow(NoSuchElementException::new);

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setLogin(userRequest.getLogin());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setSpeciality(userRequest.getSpeciality());
        user.setRole(userRequest.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
