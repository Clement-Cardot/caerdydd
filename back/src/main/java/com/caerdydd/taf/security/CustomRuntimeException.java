package com.caerdydd.taf.security;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends Exception{

    public static final String DEFAULT_MESSAGE = "An error occured";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXISTS = "User already exist";
    public static final String TEAM_NOT_FOUND = "Team not found";
    public static final String TEAM_MEMBER_NOT_FOUND = "Team member not found";
    public static final String USER_ALREADY_IN_A_TEAM = "User is already in a team";
    public static final String CURRENT_USER_IS_NOT_REQUEST_USER = "The current user is not the same as the user to update";
    public static final String SERVICE_ERROR = "Unexpected error, check database connection";


    public CustomRuntimeException(String message) {
        super(message);
    }
    
}
