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
import io.swagger.client.model.UserDTO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

/**
 * API tests for UserControllerApi
 */
public class UserControllerApiTest {

    
    static final Logger logger = Logger.getLogger(UserControllerApiTest.class.getName());

    private final ApiClient apiClient = new ApiClient();
    private final UserControllerApi api = new UserControllerApi();
    private final ApiTestingTools apiTestingTools = new ApiTestingTools(apiClient);
    
    @AfterEach
    public void tearDown() throws Exception {
        apiTestingTools.logout();
    }

    @Test
    public void testAddUser_Nominal() throws Exception {
        UserDTO userDto = null;
        String response = api.addUsingPUT(userDto);

        // TODO: test validations
    }
    
    @Test
    public void testGetUser_Nominal() throws Exception {
        Integer id = null;
        UserDTO response = api.getUsingGET1(id);

        // TODO: test validations
    }
    
    @Test
    public void testListAllUsers_Nominal() throws Exception {
        List<UserDTO> response = api.listUsingGET1();

        // TODO: test validations
    }
    
    @Test
    public void testUpdateUser_Nominal() throws Exception {
        UserDTO userDto = null;
        String response = api.updateUsingPOST(userDto);

        // TODO: test validations
    }
    
}
