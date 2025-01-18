package API_Testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    private String accessToken;
    private final String BASE_URL = "http://localhost:3003"; // Ensure the server is running here
    private final String EMAIL = "solyma.mady@hotmail.co.il";
    private final String PASSWORD = "solyma315087817";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();

        // Attempt to log in and obtain the access token
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"" + EMAIL + "\",\n" +
                        "    \"password\": \"" + PASSWORD + "\"\n" +
                        "}")
                .log().all() // Log request for debugging
                .when()
                .post("/api/v1/auth/login");

        if (response.statusCode() == 200) {
            // Extract token from response
            accessToken = response.jsonPath().getString("id");
            Assertions.assertNotNull(accessToken, "Access token should not be null");
            System.out.println("Access Token: " + accessToken);
        } else {
            // Log error details and throw exception
            System.err.println("Login failed. Status Code: " + response.statusCode());
            System.err.println("Response Body: " + response.asString());
            throw new IllegalStateException("Setup failed due to login error.");
        }
    }

    @Test
    public void validateSetup() {
        // Ensure access token is not null or empty
        Assertions.assertNotNull(accessToken, "Access token should not be null");
        Assertions.assertFalse(accessToken.isEmpty(), "Access token should not be empty");

        // Log the access token
        System.out.println("Access Token: " + accessToken);
    }
}