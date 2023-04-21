package com.caerdydd.taf.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.UserDTO;
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
            String fileExt = "." + file.getOriginalFilename().split("\\.")[1];
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + relativePath + id));
            file.transferTo( new File(System.getProperty("user.dir") + relativePath + id + "/" + type + fileExt));
            TeamDTO team = teamService.getTeamById(id);
            team.setFilePathScopeStatement(relativePath + id + "/" + type + fileExt);
            teamService.saveTeam(team);
        } catch (NullPointerException e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } catch (IOException e) {
            logger.info("Could not store the file. Error: " + e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } 
    }
}