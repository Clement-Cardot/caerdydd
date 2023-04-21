package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.RoleEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.RoleRepository;
import com.caerdydd.taf.security.CustomRuntimeException;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private TeamService teamService;

    @Spy
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testSaveFile() throws IOException, CustomRuntimeException {

        Resource testFileResource = resourceLoader.getResource(System.getProperty("user.dir") + "/back/test/resources/test");
        File testFile = testFileResource.getFile();

        // Create a MockMultipartFile to simulate the uploaded file
        MockMultipartFile mockFile = new MockMultipartFile("file", testFile.getName(), MediaType.TEXT_PLAIN_VALUE, Files.readAllBytes(testFile.toPath()));

        // Mock teamService.getTeamById() method
        TeamDTO mockedAnswer = new TeamDTO(1, "equipeTest", new ProjectDTO(), new ProjectDTO());
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);
        // Call the method to test
        // Make the API call to upload the file
        // MvcResult result = mockMvc.perform(multipart("/api/teams/upload").file(mockFile)).andExpect(status().isOk()).andReturn();

        // Verify the result
        // verify(roleRepository, times(1)).findAll();
        // assertEquals(2, result.size());
        // assertEquals(expectedAnswer.toString(), result.toString());
    }
}
