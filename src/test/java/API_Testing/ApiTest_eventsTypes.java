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


public class ApiTest_eventsTypes{

    private final String BASE_URL = "https://api.cal.com/v1";
    private final String API_KEY = TestData.API_KEY;
    private int removeEventId;
    private int getEventId;
    private int addedEventId;


    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
        removeEventId =createEventType("removeEvent","remove");
        getEventId =createEventType("getEvent","get");
    }


    @Test
    public void testValidateApiKey() {
        // Send a simple GET request to validate the API key
        Response response = given()
                .queryParam("apiKey", API_KEY)
                .when()
                .get("/event-types");
        // Validate the status code
        assertEquals(200, response.statusCode());
        // Validate the response body contains expected content
        assertTrue(response.asString().contains("event_type"));
    }

    //Get all the events
    @Test
    public void testGetEventTypes() {
        // Send the GET request
        Response response = given()
                .queryParam("apiKey", API_KEY) // Pass the API key as a query parameter
                .when()
                .get("/event-types");

        // Validate the status code
        assertEquals(200, response.statusCode());
    }


    //test create new event
    @Test
    public void testCreateEventTypeWithVariables() {
        // Define variables for title and slug
        String title = "testAPI";
        String slug = "API";

        int response = createEventType(title,slug);
        addedEventId=response;

        // Validate response
        assertTrue(response>0);
    }


    //test remove event by id
    @Test
    public void testDeleteEventType() {
        // Define the event type ID to delete
        String eventTypeId = Integer.toString(removeEventId);
       assertTrue(deleteEventType(eventTypeId));
    }


    @Test
    public void testCreateDuplicateEventType() {
        // Define variables for title and slug
        String title = "getEvent";
        String slug = "get";

        // Attempt to create the same event again using createEventType
        int duplicateEventId = createEventType(title, slug);

        // Validate that the duplicate creation fails and returns -1
        assertEquals(-1, duplicateEventId);
    }

    @Test
    public void testDeleteNonExistentEventType() {
        // Define a non-existent event ID
        String nonExistentEventId = "999999";

        // Send DELETE request
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("apiKey", API_KEY)
                .when()
                .delete("/event-types/" + nonExistentEventId);

        // Validate the response
        assertEquals(403, response.statusCode());
    }

    @AfterEach
    public void cleanup() {
        // Delete events created during the test
        if (removeEventId > 0) {
            deleteEventType(String.valueOf(removeEventId));
        }
        if (getEventId > 0) {
            deleteEventType(String.valueOf(getEventId));
        }
        if (addedEventId > 0) {
            deleteEventType(String.valueOf(addedEventId));
        }
    }



    public int createEventType(String title, String slug) {
        // Construct the JSON request body using parameters
        String requestBody = String.format("""
                       {"length": 90,
                        "title": "%s",
                        "slug": "%s"
                        }""", title, slug);

        try {
            // Send POST request
            Response response = given()
                    .baseUri(BASE_URL)
                    .queryParam("apiKey", API_KEY)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/event-types");

            // Check for success and return the event ID
            if (response.statusCode() == 200) {
                int eventId = response.jsonPath().getInt("event_type.id");
                return eventId;
            } else {
                return -1;
            }
            } catch (Exception e) {
            return -1;
        }
    }

    // Remove event by ID
    public boolean deleteEventType(String eventTypeId) {
        // Send DELETE request
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("apiKey", API_KEY)
                .when()
                .delete("/event-types/" + eventTypeId);

        // Check the response status code
        if (response.statusCode() == 200) {
            // Event deleted successfully
            return true;
        } else if (response.statusCode() == 400) {
            // Event does not exist
            return false;
        } else {
            // Handle other unexpected responses
            return false;
        }
    }



}