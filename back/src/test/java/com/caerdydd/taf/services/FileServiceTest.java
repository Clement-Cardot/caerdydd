package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private TeamService teamService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testSaveFile() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());

        try {
            fileService.saveFile(file, 1, "test");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void checkFileIsPDF_FileIsPDF() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some test".getBytes());

        // Call method to test
        try {
            fileService.checkFileIsPDF(file);
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void checkFileIsPDF_FileIsNotPDF() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "some xml".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.checkFileIsPDF(file);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }
}
