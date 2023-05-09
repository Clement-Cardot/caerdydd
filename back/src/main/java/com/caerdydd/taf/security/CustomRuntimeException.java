package com.caerdydd.taf.security;

public class CustomRuntimeException extends Exception{

    // Default Messages
    public static final String DEFAULT_MESSAGE = "An error occured";
    public static final String SERVICE_ERROR = "Service Error";
    
    // Not found messages
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TEAM_NOT_FOUND = "Team not found";
    public static final String TEAM_MEMBER_NOT_FOUND = "Team member not found";
    public static final String PROJECT_NOT_FOUND = "Can't found project";
    public static final String LINK_NOT_FOUND = "LINK_NOT_FOUND";


    // Already exist messages
    public static final String USER_ALREADY_EXISTS = "User already exist";
    public static final String USER_ALREADY_IN_A_TEAM = "User is already in a team";
    
    // Not allowed messages
    public static final String CURRENT_USER_IS_NOT_REQUEST_USER = "The current user is not the same as the user to update";
    public static final String USER_PASSWORD_NOT_MATCH = "Login or password is incorrect";
    public static final String USER_IS_NOT_A_STUDENT = "Requested user is not a student";
    public static final String USER_IS_NOT_A_TEACHING_STAFF = "Requested user is not a teaching staff";
    public static final String USER_IS_NOT_AN_OPTION_LEADER = "Requested user is not a option leader";
    public static final String USER_IS_NOT_A_TEAM_MEMBER = "Requested user is not a team member";
    public static final String USER_IS_NOT_A_PLANNING_ASSISTANT = "Requested user is not a planning assistant";

    // Règles métiers messages <- A traduire en anglais ^^
    public static final String NB_TEAMS_SHOULD_BE_EVEN = "The number of teams should be even";
    public static final String TEAM_IS_FULL = "The team is full";
    public static final String TEAM_ALREADY_HAS_2_CSS = "The team already has 2 CSS";
    public static final String TEAM_ALREADY_HAS_4_LD = "The team already has 4 LD";
    public static final String TEAM_MEMBER_IMPOSSIBLE_TOTAL_MARK = "The total mark of the team member is under 0 or above 20";
    public static final String TEAM_MEMBER_INCORRECT_BONUS_PENALTY = "The bonus of the team member is under -4 or above 4";
    public static final String USER_ID_SHOULD_BE_NULL = "User id should be null";
    public static final String USER_NOT_IN_ASSOCIATED_TEAM = "User is not associated in a team";

    // File messages
    public static final String INCORRECT_FILE_FORMAT = "Incorrect file format";
    public static final String FILE_EXCEPTION = "File exception";
    public static final String FILE_IS_EMPTY = "File is empty";
    
    // Invalid messages
    public static final String INVALID_LINK = "The provided text is not a valid link";
    
    public CustomRuntimeException(String message) {
        super(message);
    }
    
}
