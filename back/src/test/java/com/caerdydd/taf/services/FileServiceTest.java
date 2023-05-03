package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        int id = 1;
        String type = "test.pdf";
        String expectedFilePath = "/upload/equipe" + id + "/" + type ;
        // Resource testFileResource = resourceLoader.getResource(System.getProperty("user.dir") + "/back/test/resources/test");
        File testFile = new File(System.getProperty("user.dir") + "/back/src/test/resources/test");

        // Create a MockMultipartFile to simulate the uploaded file
        MockMultipartFile mockFile = new MockMultipartFile("file", testFile.getName(), MediaType.TEXT_PLAIN_VALUE, Files.readAllBytes(testFile.toPath()));
        
        // Call the method to test
        fileService.saveFile(mockFile, id, type);

        
        // Verify the result
        assertTrue(Files.exists(Paths.get(System.getProperty("user.dir") + expectedFilePath)));
        Files.deleteIfExists(Paths.get(System.getProperty("user.dir") + expectedFilePath));
    }
}
