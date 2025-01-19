# Cal.com Testing Repository - Automation Project

This repository includes automated tests for the Scheduling application hosted at http://localhost:3000. The tests validate the core functionalities, such as adding, removing, and retrieving events, while also covering scenarios for IP-based testing. Additionally, we use ngrok to expose the local server, enabling seamless test execution in GitHub workflows.

- **UI Testing**: Implemented using Selenium to validate key user interactions. The primary focus is on the add and remove functionalities for events in the user interface.
- **API Testing**: Written with RestAssured to verify backend functionality. The main emphasis is on testing add and remove operations for event types via API endpoints.

### Strategy and Objectives

The primary objective of these tests is to validate the core features of the events scheduling platform and associated functionalities. We focus on verifying the following:

- Login Validation:
    - Login with valid credentials.
    - Attempt login with invalid credentials.  <br>
      <br>
- Team Management:
     - Add a new team.
     - Add new members to a team.
     - Prevent adding team names that already exist.
     - Prevent adding members who are already part of the team.
     - Remove a team.  <br>
       <br>
- Event Management (API Testing)
   - Add New Event
      - Ensures a new event can be created with the correct `title`, `slug`, and `length`.
      - Validates the response to confirm the event is successfully created and an `event_type.id` is returned. 
   - Edit Event
     - Verifies that an existing event's schedule (e.g., `date` and `time`) can be modified successfully. 
   - Delete Event
     - Ensures that an event can be deleted using its `id`.
      - Validates the response to confirm the event is no longer retrievable. 
   - Delete Booking
     - Ensures a booking can be deleted and verifies the response for successful operation. 
   - Get All Events
     - Retrieves a list of all available events.
     - Validates that the response contains the expected data. 
   - Get Event by ID
     - Retrieves a specific event using its unique `id`.
     - Validates that the event details in the response match the expected data. 
   - Validate API Key
     - Sends a simple GET request to validate the API key.
     - Confirms that a valid API key returns a `200` status code and expected response content. 
   - Create Duplicate Event
     - Tests the behavior when attempting to create an event with the same `title` and `slug` as an existing event.
     - Validates that the duplicate creation fails with the expected error response. 
   - Delete Non-Existent Event
     - Sends a DELETE request for an event ID that does not exist.
     - Verifies that the API responds with a `403` status code and the appropriate error message.

### Setup and Requirements
To run these tests, ensure the following prerequisites are met:
- **Node.js** (version 16 or above) is installed.
- **npm** (Node Package Manager) is available.
- **Docker** (version 19 or above) is installed.

1. **Clone this repository:**
   ```bash
   git clone https://github.com/baraah99/CalComProject.git
2. **Set up the Cal.com database using Docker:**
   ```bash
   git clone https://github.com/calcom/docker
   cd docker
   docker-compose up -d

3. **Install the required dependencies:**
   ```bash
   cd /path/to/CalComProject
   npm install

4. **Run the tests:**
   ```bash
   npm test

