package com.caerdydd.taf.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> list() {
        try {
            List<UserDTO> users = userService.listAllUsers().stream()
                                .map(user -> modelMapper.map(user, UserDTO.class))
                                .collect(Collectors.toList()) ;
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Integer id) {
        try {
            UserDTO user = modelMapper.map(userService.getUserById(id), UserDTO.class);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<UserDTO> add(@RequestBody UserDTO userDto) {

        UserEntity userRequest = modelMapper.map(userDto, UserEntity.class);

        UserEntity user = userService.saveUser(userRequest);

        UserDTO userResponse = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDto) {

        UserEntity userRequest = modelMapper.map(userDto, UserEntity.class);

        UserEntity user = userService.updateUser(id, userRequest);

        UserDTO userResponse = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    
}
