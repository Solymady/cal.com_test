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
        Response response = given().header("Authorization", API_KEY).when()
                .get("/event-types"); // Adjust endpoint as needed

        // Validate the status code
        assertEquals(200, response.statusCode(), "Expected status code 200 but got " + response.statusCode());

        // Validate the response body contains expected content
        assertTrue(response.asString().contains("event_type"), "Response should contain 'event_type'");
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
        assertEquals(200, response.statusCode(), "Expected status code 200 but got " + response.statusCode());
    }

    //Get event by id
    @Test
    public void testGetEventTypeById() {
        // Replace with the specific event type ID
        int eventTypeId = getEventId;

        // Send the GET request
        Response response = given()
                .queryParam("apiKey", API_KEY) // Pass the API key as a query parameter
                .when()
                .get("/event-types/" + eventTypeId);

        // Validate the status code
        assertEquals(200, response.statusCode(), "Expected status code 200 but got " + response.statusCode());
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
        String eventTypeId = Integer.toString(removeEventId); // Replace with the actual ID
       assertTrue(deleteEventType(eventTypeId));
    }


    @Test
    public void testCreateDuplicateEventType() {
        // Define variables for title and slug
        String title = "getEvent";
        String slug = "get";

        // Attempt to create the same event again using createEventType
        int duplicateEventId = createEventTypeWithRetry(title, slug);

        // Validate that the duplicate creation fails and returns -1
        assertEquals(-1, duplicateEventId, "Expected duplicate event creation to fail, but it succeeded.");
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

        // Print the response for debugging
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asString());

        // Validate the response
        assertEquals(403, response.statusCode(), "Expected status code 403 for non-existent event deletion but got " + response.statusCode());
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

    private int createEventTypeWithRetry(String title, String slug) {
        int retries = 3; // Number of retries
        int waitTime = 2000; // Wait time between retries in milliseconds

        for (int i = 0; i < retries; i++) {
            int eventId = createEventType(title, slug);
            if (eventId > 0 || eventId == -1) {
                return eventId; // Success or expected failure
            }
            try {
                System.out.println("Retrying after rate limit... (" + (i + 1) + "/" + retries + ")");
                Thread.sleep(waitTime); // Wait before retrying
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        throw new RuntimeException("Failed to create event after " + retries + " attempts due to rate limits.");
    }

    public int createEventType(String title, String slug) {
        // Construct the JSON request body using parameters
        String requestBody = String.format("""
    {
        "length": 90,
        "title": "%s",
        "slug": "%s"
    }
    """, title, slug);

        // Print request body for debugging
        System.out.println("Request Body: " + requestBody);

        try {
            // Send POST request
            Response response = given()
                    .baseUri(BASE_URL)
                    .queryParam("apiKey", API_KEY)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/event-types");

            // Print response for debugging
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.asString());

            // Check for success and return the event ID
            if (response.statusCode() == 200) {
                int eventId = response.jsonPath().getInt("event_type.id");
                System.out.println("Created Event ID: " + eventId);
                return eventId;
            } else {
                System.err.println("Failed to create event. Status Code: " + response.statusCode());
                return -1;
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            System.err.println("An error occurred while creating the event: " + e.getMessage());
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

        // Print response details for debugging
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asString());

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