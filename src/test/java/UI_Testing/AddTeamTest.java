package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
import org.example.pages.team.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddTeamTest {
    private WebDriver driver;
    private TeamsPage teamsPage;
    private EventTypesPage eventTypesPage;
    private String teamName = "testAddTeam";


    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {
        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/auth/login");

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser("solyma.mady@hotmail.co.il", "Solyma315087817");

        // Navigate to Teams page
        teamsPage = new TeamsPage(driver);
    }

    @Test
    public void testAddNewTeam() throws InterruptedException {
        //Step1: navigate To Teams Page
        EventTypesPage eventTypesPage=new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();

        // Step 2: Click Add New Team
        TeamsPage teamsPage=new TeamsPage(driver);
        teamsPage.clickAddNewTeam();

       // Step 3: Create a new team
        CreateTeamPage createTeamPage = new CreateTeamPage(driver);
        createTeamPage.setTeamName(teamName);
        createTeamPage.clickContinue();

        // Step 4: Add team members
        AddTeamMembersPage addTeamMembersPage = new AddTeamMembersPage(driver);

        // Add Member 1
        addTeamMembersPage.addMember("member1@gmail.com","Admin");
        Thread.sleep(1000);
        // Add Member 2
        addTeamMembersPage.addMember("member2@gmail.com","Member");
        Thread.sleep(1000);
        addTeamMembersPage.clickContinue();

        //Step 5 : Add new team event
        AddNewTeamEvent addNewTeamEvent=new AddNewTeamEvent(driver);
        addNewTeamEvent.fillNewTeamEvent("test","ROUND_ROBIN");

        //Step 6 : Go back to event type page
        TeamProfile teamProfile=new TeamProfile(driver);
        teamProfile.clickBackButton();

        //Step 7 : Go to teams page
        eventTypesPage.navigateToTeamsPage();
        Thread.sleep(1000);

        //Step 8 : Check if team exists
        assertTrue(teamsPage.isTeamExists(teamName));


    }

    @AfterEach
    public void tearDown() {
        TeamsPage teamsPage=new TeamsPage(driver);
        if (driver != null) {
            if(teamsPage.isTeamExists(teamName)){
                teamsPage.removeTeam(teamName);
            }
            driver.quit();
        }
    }
}
