package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.FileRules;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private TeamService teamService;

    @Mock
    private Environment env;

    @Spy
    private ModelMapper modelMapper;

    @Mock 
    private FileRules fileRules;

    @Test
    void testSaveFile_Nominal() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));
        
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        try {
            fileService.saveFile(file, 1, "teamScopeStatement");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testSaveFile_TheFileIsAnalysis() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));
        
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        try {
            fileService.saveFile(file, 1, "analysis");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testSaveFile_TheFileIsFinalScopeStatement() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));
        
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        try {
            fileService.saveFile(file, 1, "finalTeamScopeStatement");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testSaveFile_TheFileIsReport() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));
        
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        try {
            fileService.saveFile(file, 1, "report");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveFile_TeamIsNotPresent() throws IOException, CustomRuntimeException {
        when(teamService.getTeamById(1)).thenThrow(new NullPointerException());

        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Create a empty file
        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.saveFile(file, 1, "test");
        });

        // Assertions
        assertEquals(CustomRuntimeException.TEAM_NOT_FOUND, exception.getMessage());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveFile_CantSave() throws IOException, CustomRuntimeException {
        MultipartFile file = mock(MultipartFile.class);
        doThrow(new IOException()).when(file).transferTo(any(File.class));
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.saveFile(file, 1, "test");
        });

        // Assertions
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveFile_FileIsNotPdf() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Mock fileRules.checkFileIsPDF()
        doThrow(new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT)).when(fileRules).checkFileIsPDF(any(MultipartFile.class));

        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.saveFile(file, 1, "test");
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testLoadFileAsResource_Nominal() throws CustomRuntimeException {
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());
        
        fileService.saveFile(file, 1, "teamScopeStatement");

        try {
            fileService.loadFileAsResource(1, "test");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testLoadFileAsResource_TheFileIsNotPresent() throws CustomRuntimeException {
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.loadFileAsResource(1, "notExistingFile");
        });

        // Assertions
        assertEquals(CustomRuntimeException.FILE_NOT_FOUND, exception.getMessage());
    }
}
