package com.caerdydd.taf.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class FileService {

    private static final Logger logger = LogManager.getLogger(FileService.class);

    @Autowired
    private TeamService teamService;

    public void saveFile(MultipartFile file, int id, String type) throws CustomRuntimeException {
        try {
            final String relativePath = "/upload/equipe";
            String fileExt = "";
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExt = "." + originalFilename.split("\\.")[1];
            }
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + relativePath + id));
            this.checkFileIsPDF(file);
            file.transferTo(new File(System.getProperty("user.dir") + relativePath + id + "/" + type + fileExt));
            TeamDTO team = teamService.getTeamById(id);
            team.setFilePathScopeStatement(relativePath + id + "/" + type + fileExt);
            teamService.saveTeam(team);
            logger.info("File saved at this location : " + (relativePath + id + "/" + type + fileExt));
        } catch (NullPointerException e) {
            logger.warn("The team you are trying to add a file is not existing: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } catch (IOException e) {
            logger.warn("Could not store the file. Error: " + e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } 
    }

    public Resource retrieveFile(String filePath) throws CustomRuntimeException, MalformedURLException {
            Path file = Paths.get(filePath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
    }

    public void checkFileIsPDF(MultipartFile file) throws CustomRuntimeException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        try {
            if (!fileName.endsWith(".pdf")){
                throw new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT);
            }
        } catch (NullPointerException e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        
    }
}