package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.user.RoleEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.RoleRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserService userService;
    

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testListAllRoles_Nominal(){
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
    void testListAllRoles_Empty(){
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
    void testListAllRoles_ServiceError(){
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
    void testSaveRole_Nominal(){
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


        @Test
    void testGetRoleById_Nominal() throws CustomRuntimeException {
        // Given
        Integer roleId = 1;
        RoleEntity mockRoleEntity = new RoleEntity(roleId, "STUDENT_ROLE", new UserEntity("firstname1", "lastname1", "login1", "password1", "email1", "LD"));
        RoleDTO expectedRole = new RoleDTO(roleId, "STUDENT_ROLE", new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD"));
        when(roleRepository.findByIdRole(roleId)).thenReturn(mockRoleEntity);

        // When
        RoleDTO result = roleService.getRoleById(roleId);

        // Then
        verify(roleRepository, times(1)).findByIdRole(roleId);
        assertEquals(expectedRole.toString(), result.toString());
    }

    @Test
    void testDeleteRole_Nominal() throws CustomRuntimeException {
        // Given
        RoleDTO roleToDelete = new RoleDTO(1, "STUDENT_ROLE", new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD"));
        doNothing().when(roleRepository).delete(any(RoleEntity.class));

        // When
        roleService.deleteRole(roleToDelete);

        // Then
        verify(roleRepository, times(1)).delete(any(RoleEntity.class));
    }

    @Test
    void testAssignRoleToUser_Nominal() throws CustomRuntimeException {
        // Given
        Integer userId = 1;
        String roleName = "STUDENT_ROLE";
        UserDTO mockUser = new UserDTO(userId, "firstname1", "lastname1", "login1", "password1", "email1", "LD");
        RoleDTO expectedRole = new RoleDTO(roleName, mockUser);
        when(userService.getUserById(userId)).thenReturn(mockUser);
        when(userService.updateUser(mockUser)).thenReturn(mockUser);

        // When
        RoleDTO result = roleService.assignRoleToUser(userId, roleName);

        // Then
        verify(userService, times(1)).getUserById(userId);
        verify(userService, times(1)).updateUser(mockUser);
        assertEquals(expectedRole.toString(), result.toString());
    }


        @Test
        void testGetRoleById_ThrowException() {
            // Given
            when(roleRepository.findByIdRole(anyInt())).thenThrow(RuntimeException.class);

            // Then
            assertThrows(CustomRuntimeException.class, () -> roleService.getRoleById(1));
            verify(roleRepository, times(1)).findByIdRole(anyInt());
    }

    @Test
    void testDeleteRole_ThrowException() {
        // Given
        RoleDTO roleToDelete = new RoleDTO(1, "STUDENT_ROLE", new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD"));
        doThrow(RuntimeException.class).when(roleRepository).delete(any(RoleEntity.class));

        // Then
        assertThrows(CustomRuntimeException.class, () -> roleService.deleteRole(roleToDelete));
        verify(roleRepository, times(1)).delete(any(RoleEntity.class));
    }

    @Test
    void testAssignRoleToUser_ThrowException() throws CustomRuntimeException {
        // Given
        when(userService.getUserById(anyInt())).thenThrow(RuntimeException.class);

        // Then
        assertThrows(CustomRuntimeException.class, () -> roleService.assignRoleToUser(1, "STUDENT_ROLE"));
        verify(userService, times(1)).getUserById(anyInt());
    }


    @Test
    void testAssignRoleToUser_ThrowCustomRuntimeException() throws CustomRuntimeException {
        // Given
        when(userService.getUserById(anyInt())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Then
        assertThrows(CustomRuntimeException.class, () -> roleService.assignRoleToUser(1, "STUDENT_ROLE"));
        verify(userService, times(1)).getUserById(anyInt());
    }



    
}
