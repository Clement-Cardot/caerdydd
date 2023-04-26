package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.RoleEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.RoleRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void testListAllRoles_Nominal(){
        // Mock teamRepository.findAll() method
        List<RoleEntity> mockedAnswer = new ArrayList<RoleEntity>();
        mockedAnswer.add(new RoleEntity(1, "STUDENT_ROLE", new UserEntity("firstname1", "lastname1", "login1", "password1", "email1", "LD")));
        mockedAnswer.add(new RoleEntity(2, "TEACHER_ROLE", new UserEntity("firstname2", "lastname2", "login2", "password2", "email2", null)));
        when(roleRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<RoleDTO> expectedAnswer = new ArrayList<RoleDTO>();
        expectedAnswer.add(new RoleDTO(1, "STUDENT_ROLE", new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD")));
        expectedAnswer.add(new RoleDTO(2, "TEACHER_ROLE", new UserDTO("firstname2", "lastname2", "login2", "password2", "email2", null)));

        // Call the method to test
        List<RoleDTO> result = new ArrayList<RoleDTO>();
        try {
            result = roleService.listAllRoles();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(roleRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testListAllRoles_Empty(){
        // Mock teamRepository.findAll() method
        List<RoleEntity> mockedAnswer = new ArrayList<RoleEntity>();
        when(roleRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<RoleDTO> expectedAnswer = new ArrayList<RoleDTO>();
        
        // Call the method to test
        List<RoleDTO> result = new ArrayList<RoleDTO>();
        try {
            result = roleService.listAllRoles();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(roleRepository, times(1)).findAll();
        assertEquals(0, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testListAllRoles_ServiceError(){
        // Mock teamRepository.findAll() method
        when(roleRepository.findAll()).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            roleService.listAllRoles();
        });

        // Verify the result
        verify(roleRepository, times(1)).findAll();
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    public void testSaveRole_Nominal(){
        // Mock teamRepository.save() method
        RoleEntity mockedAnswer = new RoleEntity(1, "STUDENT_ROLE", new UserEntity("firstname1", "lastname1", "login1", "password1", "email1", "LD"));
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(mockedAnswer);

        // Define the expected answer
        RoleDTO roleToSave = new RoleDTO(1, "STUDENT_ROLE", new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD"));

        // Call the method to test
        RoleDTO result = roleService.saveRole(roleToSave);

        // Verify the result
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
        assertEquals(roleToSave.toString(), result.toString());
    }
    
}
