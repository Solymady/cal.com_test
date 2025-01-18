package com.example.project.constants;

public class TestData {

    // Local URLs
    public static final String LOCAL_BASE_URL = "http://localhost:3000";
    public static final String LOCAL_LOGIN_ENDPOINT = LOCAL_BASE_URL + "/auth/login";
    public static final String LOCAL_EVENT_ENDPOINT = LOCAL_BASE_URL + "/event-types";
    public static final String LOCAL_TEAMS_ENDPOINT = LOCAL_BASE_URL + "/teams";

    // Ngrok URLs
    public static final String NGROK_BASE_URL = "https://example.ngrok.io";
    public static final String NGROK_LOGIN_ENDPOINT = NGROK_BASE_URL + "/auth/login";
    public static final String NGROK_EVENT_ENDPOINT = NGROK_BASE_URL + "/event-types";
    public static final String NGROK_TEAMS_ENDPOINT = NGROK_BASE_URL + "/teams";

    // API
    public static final String LOCAL_BASE_API_URL = "https://api.cal.com/v1";
    public static final String API_KEY ="cal_live_2725c576aa5eb86fbfc552fa289b916e";

    //Login test valid data
    public static final String VALID_EMAIL ="solyma.mady@hotmail.co.il"
    public static final String VALID_PASSWORD = "Admin123456789admin"

    //Login test Invalid data
    public static final String INVALID_EMAIL = "invalid@example.com"
    public static final String INVALID_PASSWORD = "wrongpassword"


    // Add Test Data
    public static final String ADD_TEAM_NAME ="testAddTeam";
    public static final String MEMBER1_EMAIL="member1@gmail.com";
    public static final String MEMBER1_ROLE="Admin";
    public static final String MEMBER2_EMAIL="member2@gmail.com";
    public static final String MEMBER2_ROLE="Member";

    //new team event
    public static final String TEAM_EVENT_TITLE="test";
    public static final String TEAM_EVENT_TYPE="ROUND_ROBIN";

    //Remove Team
    public static final String REMOVE_TEAM_NAME = "removeTeam";

    //Team name exist
    public static final String EXIST_TEAM_NAME = "testExist";
    public static final String EXIST_TEAM_NAME_ERROR_MESSAGE = "This URL is already taken";

    //Member exist
    public static final String MEMBER_EXIST_EMAIL="member1@gmail.com";
    public static final String MEMBER_EXIST_ROLE="Member";
    public static final String EXIST_TEAM_MEMBER_ERROR_MESSAGE = "Member has already been invited";






}
