/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.model.ProjectDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ProjectControllerApi
 */
public class ProjectControllerApiTest {

    private final ProjectControllerApi api = new ProjectControllerApi();

    
    /**
     * updateProject
     *
     * 
     *
     * @throws Exception
     *          if the Api call fails
     */
    @Test
    public void updateProjectUsingPUTTest() throws Exception {
        ProjectDTO projectDTO = null;
        Integer projectId = null;
        Integer teamId = null;
        Integer userId = null;
        ProjectDTO response = api.updateProjectUsingPUT(projectDTO, projectId, teamId, userId);

        // TODO: test validations
    }
    
}
