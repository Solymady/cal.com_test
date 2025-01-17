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
    private final String BASE_URL = "http://localhost:3000";
    private final String EMAIL = "solyma.mady@hotmail.co.il";
    private final String PASSWORD = "solyma315087817";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI =BASE_URL;// Use HTTPS if required
        RestAssured.useRelaxedHTTPSValidation(); // For self-signed certificates (if applicable)

        // Login and get access token
        accessToken = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"" + EMAIL + "\",\n" +
                        "    \"password\": \"" + PASSWORD + "\"\n" +
                        "}")
                .log().all() // Log request
                .when()
                .post("/api/v1/session")
                .then()
                .log().all() // Log response
                .statusCode(200) // Expect 200 OK
                .extract()
                .path("id");
    }


    @Test
    public void validateSetup() {
        // Check if the access token is not null or empty
        Assertions.assertNotNull(accessToken, "Access token should not be null");
        Assertions.assertFalse(accessToken.isEmpty(), "Access token should not be empty");

        // Print the access token for debugging purposes
        System.out.println("Access Token: " + accessToken);
    }


    /**
    // Function to GET event types
    @Test
    public void getEventTypes() {
        given()
                .header("Authorization", "Bearer " + accessToken) // Add token to the header
                .when()
                .get("/event-types")
                .then()
                .statusCode(200)
                .body("", not(empty())) // Ensure the response body is not empty
                .body("size()", greaterThan(0)) // Ensure at least one event type exists
                .log().all();
    }

    // Function to POST a new event type
    @Test
    public void postNewEventType() {
        String newEventType = "{\n" +
                "    \"name\": \"New Event\",\n" +
                "    \"description\": \"This is a test event type.\"\n" +
                "}";

        given()
                .header("Authorization", "Bearer " + accessToken) // Add token to the header
                .contentType(ContentType.JSON)
                .body(newEventType)
                .when()
                .post("/event-types")
                .then()
                .statusCode(201)
                .body("name", equalTo("New Event")) // Validate the created event type's name
                .log().all();
    }

    // Function to PUT (update) an event type
    @Test
    public void updateEventType() {
        String updatedEventType = "{\n" +
                "    \"name\": \"Updated Event\",\n" +
                "    \"description\": \"This is an updated test event type.\"\n" +
                "}";

        given()
                .header("Authorization", "Bearer " + accessToken) // Add token to the header
                .contentType(ContentType.JSON)
                .body(updatedEventType)
                .when()
                .put("/event-types/1") // Replace '1' with the actual ID of the event type to update
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Event")) // Validate the updated event type's name
                .log().all();
    }

    // Function to DELETE an event type
    @Test
    public void deleteEventType() {
        given()
                .header("Authorization", "Bearer " + accessToken) // Add token to the header
                .when()
                .delete("/event-types/1") // Replace '1' with the actual ID of the event type to delete
                .then()
                .statusCode(204) // Validate successful deletion
                .log().all();
    }**/
}
