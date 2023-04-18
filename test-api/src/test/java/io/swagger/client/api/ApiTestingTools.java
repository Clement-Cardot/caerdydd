package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.model.UserDTO;

public class ApiTestingTools {

        private final AuthControllerApi authApi;

        public ApiTestingTools(ApiClient apiClient) {
                this.authApi = new AuthControllerApi(apiClient);
        }
        
        public Boolean login(String login, String password){
                UserDTO user = new UserDTO();
                user.setLogin(login);
                user.setPassword(password);
                try {
                        authApi.loginUsingPOST(user);
                } catch (ApiException e) {
                        return false;
                }
                return true;
        }

        public Boolean logout(){
                try {
                        authApi.logoutUserUsingPOST();
                } catch (ApiException e) {
                        return false;
                }
                return true;
        }
}
