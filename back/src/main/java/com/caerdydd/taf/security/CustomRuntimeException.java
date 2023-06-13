package com.caerdydd.taf.security;

public class CustomRuntimeException extends Exception{

    // Default Messages
    public static final String DEFAULT_MESSAGE = "An error occured";
    public static final String SERVICE_ERROR = "Service Error";
    
    // Not found messages
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TEACHING_STAFF_NOT_FOUND = "Teaching staff not found";
    public static final String TEAM_NOT_FOUND = "Team not found";
    public static final String TEAM_MEMBER_NOT_FOUND = "Team member not found";
    public static final String PROJECT_NOT_FOUND = "Can't found project";
    public static final String LINK_NOT_FOUND = "LINK_NOT_FOUND";
    public static final String PRESENTATION_NOT_FOUND = "Presentation not found";
    public static final String PLANNED_TIMING_CONSULTING_NOT_FOUND = "Planned timing consulting not found";
    public static final String PLANNED_TIMING_AVAILABILITY_NOT_FOUND = "Planned timing availability not found";
    public static final String CONSULTING_NOT_FOUND = "Consulting not found";
    public static final String NOTIFICATION_NOT_FOUND = "Notification not found";
    
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
    public static final String USER_IS_NOT_AUTHORIZED = "Requested user is not authorized";
    public static final String USER_IS_NOT_A_JURY_MEMBER = "Requested user is not a jury member";

    // Règles métiers messages <- A traduire en anglais ^^
    public static final String NB_TEAMS_SHOULD_BE_EVEN = "The number of teams should be even";
    public static final String TEAM_IS_FULL = "The team is full";
    public static final String TEAM_ALREADY_HAS_2_CSS = "The team already has 2 CSS";
    public static final String TEAM_ALREADY_HAS_4_LD = "The team already has 4 LD";
    public static final String TEAM_MEMBER_IMPOSSIBLE_TOTAL_MARK = "The total mark of the team member is under 0 or above 20";
    public static final String TEAM_MEMBER_INCORRECT_BONUS_PENALTY = "The bonus of the team member is under -4 or above 4";
    public static final String USER_ID_SHOULD_BE_NULL = "User id should be null";
    public static final String USER_NOT_IN_ASSOCIATED_TEAM = "User is not associated in a team";
    public static final String USER_NOT_IN_A_TEAM = "User is not in a team";
    public static final String USER_IS_NOT_OWNER_OF_AVAILABILITY = "User is not owner of availability";
    public static final String PLANNED_TIMING_IS_IN_PAST = "Planned timing is in past";
    public static final String PLANNED_TIMING_IS_ALREADY_TAKEN = "Planned timing is already taken";
    public static final String DEMAND_IS_MADE_TOO_LATE = "Demand is made too late";
    public static final String CONSULTING_IS_IN_PAST = "Consulting is in past";
    public static final String TEACHING_STAFF_IS_NOT_AVAILABLE = "Teaching staff is not available";
    public static final String TEAM_MEMBER_INCORRECT_INDIVIDUAL_MARK = "The individual mark of the team member is under 0 or above 10";
    public static final String TEAM_MEMBER_INCORRECT_TEAM_MARK = "The team mark of the team member is under 0 or above 5";

    // File messages
    public static final String INCORRECT_FILE_FORMAT = "Incorrect file format";
    public static final String FILE_EXCEPTION = "File exception";
    public static final String FILE_IS_EMPTY = "File is empty";
    public static final String FILE_NOT_FOUND = "File not found";
    
    // Invalid messages
    public static final String INVALID_LINK = "The provided text is not a valid link";
    
    // Rules for jury
    public static final String JURY_NOT_FOUND = "Jury not found";
    public static final String JURY_ALREADY_EXISTS = "This jury already exists";
    public static final String TEACHING_STAFF_ARE_THE_SAME = "You cannot create a jury with only one teaching staff";

    //Not available 
    public static final String ROOM_NOT_AVAILABLE = "Room is not available";
    public static final String JURY_NOT_AVAILABLE = "Jury is not available ";
    public static final String TEACHING_STAFF_NOT_AVAILABLE = "Teaching Staff is not available ";

    //time 
    public static final String PRESENTATION_END_BEFORE_BEGIN = "Presentation end time is before its start time.";
    public static final String PRESENTATION_DID_NOT_BEGIN = "Presentation did not start yet.";

    public CustomRuntimeException(String message) {
        super(message);
    }
    
}
