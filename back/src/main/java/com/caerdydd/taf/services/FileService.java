package com.caerdydd.taf.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment env;

    public void saveFile(MultipartFile multipartFile, int id, String type) throws CustomRuntimeException {
        try {
            this.checkFileIsPDF(multipartFile);
            
            String path = env.getProperty("file.upload-dir") + String.format("/equipe%d/", id);
            String fileName = type + ".pdf";

            Files.createDirectories(Paths.get(path));
            multipartFile.transferTo(new File(path, fileName));

            TeamDTO team = teamService.getTeamById(id);

            if (type.equals("teamScopeStatement")) {
                team.setFilePathScopeStatement(path + "/" + fileName);
            } else if (type.equals("analysis")) {
                team.setFilePathScopeStatementAnalysis(path + "/" + fileName);
            } else if (type.equals("finalTeamScopeStatement")) {
                team.setFilePathFinalScopeStatement(path + "/" + fileName);
            }

            teamService.saveTeam(team);
            logger.info("File saved at this location : {} {}", path, fileName);

        } catch (NullPointerException e) {
            logger.error("The team you are trying to add a file is not existing: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR); // TODO change for a NOT FOUND Message Exception
        } catch (IOException e) {

            logger.error("Could not store the file. Error: {}", e.getMessage());
            logger.error(e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } 
    }

    public Resource loadFileAsResource(int idTeam, String type) throws CustomRuntimeException {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir") + "/upload/equipe" + idTeam + "/" + type + ".pdf").toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                logger.warn("File not found for path {}", filePath);
                throw new CustomRuntimeException(CustomRuntimeException.FILE_NOT_FOUND);
            }
        } catch (MalformedURLException ex) {
            logger.warn("File not found {}, {}", type, ex);
            throw new CustomRuntimeException(CustomRuntimeException.FILE_NOT_FOUND);
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