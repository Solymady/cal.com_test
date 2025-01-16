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
    private String teamName = "testAddTeam";

    @BeforeEach
    public void setUp() throws MalformedURLException {
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
        // Add Member 3
        //addTeamMembersPage.addMember("member3@gmail.com","Owner");
        addTeamMembersPage.clickContinue();

        AddNewTeamEvent addNewTeamEvent=new AddNewTeamEvent(driver);
        addNewTeamEvent.fillNewTeamEvent("test","ROUND_ROBIN");

        TeamProfile teamProfile=new TeamProfile(driver);
        teamProfile.clickBackButton();

        eventTypesPage.navigateToTeamsPage();
        Thread.sleep(1000);
        assertTrue(teamsPage.isTeamExists(teamName));

        teamsPage.removeTeamMember(teamName);
    }

    @Test
    public void testExistTeamTitle(){
        teamName="testExist";
        //Step1: navigate To Teams Page
        EventTypesPage EventTypesPage=new EventTypesPage(driver);
        EventTypesPage.navigateToTeamsPage();

        // Step 2: Click Add New Team
        TeamsPage TeamsPage=new TeamsPage(driver);
        TeamsPage.clickAddNewTeam();

        // Step 3: Create a new team
        CreateTeamPage createTeamPage = new CreateTeamPage(driver);
        createTeamPage.setTeamName(teamName);
        createTeamPage.clickContinue();
        assertEquals("This URL is already taken",createTeamPage.getErrorMessageForExistTeamTitle());
    }


    @AfterEach
    public void tearDown() {
        TeamsPage teamsPage=new TeamsPage(driver);
        if (driver != null) {
            //teamsPage.removeTeamMember(teamName);
            driver.quit();
        }
    }
}
