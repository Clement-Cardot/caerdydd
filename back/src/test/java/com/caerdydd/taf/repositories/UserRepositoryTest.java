package com.caerdydd.taf.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caerdydd.taf.models.entities.UserEntity;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void listAllUserTest() throws Exception {
        List<UserEntity> users = userRepository.findAll();
        
        assertEquals(6, users.size());
    }

    @Test
    public void findByIdTest() throws Exception {
        UserEntity user = userRepository.findById(1).get();
        
        assertEquals("Jean", user.getName());
        assertEquals("Dupont", user.getSurname());
        assertEquals("jdupont", user.getLogin());
        assertEquals("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q", user.getPassword());
        assertEquals("jean.dupont@reseau.eseo.fr", user.getEmail());
        assertEquals("student", user.getRole());
    }

    @Test
    public void findByLoginTest() throws Exception {
        Optional<UserEntity> user = userRepository.findByLogin("jdupont");
        
        assertEquals(1, user.get().getId());
    }

    
}
