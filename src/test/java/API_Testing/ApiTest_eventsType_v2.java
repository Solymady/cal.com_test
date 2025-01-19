package API_Testing;



import UI_Testing.TestData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class ApiTest_eventsType_v2{

    private final String BASE_URL = "https://api.cal.com/v2";
    private String accessToken;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void testValidateApiKey() {
        // Make the GET request
        Response response = RestAssured
                .given()
                .baseUri(baseURI)
                .header("Authorization", "Bearer " + TestData.API_KEY)
                .when()
                .get("/provider/" + 1333622 + "/access-token");

        // Print the response details
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.prettyPrint());

        // Validate the response
        assertEquals(200, response.statusCode(), "Expected status code 200 but got " + response.statusCode());
        assertTrue(response.asString().contains("access_token"), "Response should contain 'access_token'");
    }



}