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

import io.swagger.client.ApiClient;
import io.swagger.client.model.TeamMemberDTO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;
import java.util.logging.Logger;

/**
 * API tests for TeamMemberControllerApi
 */
public class TeamMemberControllerApiTest {

    static final Logger logger = Logger.getLogger(TeamMemberControllerApiTest.class.getName());

    private final ApiClient apiClient = new ApiClient();
    private final TeamMemberControllerApi api = new TeamMemberControllerApi();
    private final ApiTestingTools apiTestingTools = new ApiTestingTools(apiClient);
    

    @AfterEach
    public void tearDown() throws Exception {
        apiTestingTools.logout();
    }
    
    @Test
    public void testGetTeamMembers_Nominal() throws Exception {
        Integer id = null;
        TeamMemberDTO response = api.getUsingGET(id);

        // TODO: test validations
    }
    
    @Test
    public void testListAllTeamMembers_Nominal() throws Exception {
        List<TeamMemberDTO> response = api.listUsingGET();

        // TODO: test validations
    }

    @Test
    public void testSetBonusPenalty_Nominal() throws Exception {
        Integer bonus = null;
        Integer id = null;
        TeamMemberDTO response = api.setBonusPenaltyUsingPOST(bonus, id);

        // TODO: test validations
    }
    
}
