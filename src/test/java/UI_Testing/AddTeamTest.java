package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
import org.example.pages.team.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.example.DriverFactory.getDriver;
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
        driver = getDriver();
        driver.manage().window().maximize();
        driver.get(TestData.NGROK_BASE_URL);

        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            System.out.println("Ngrok warning page was not loaded");
        }

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser("solyma.mady@hotmail.co.il", "Admin123456789admin");

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
    public void tearDown() throws InterruptedException {
        TeamsPage teamsPage=new TeamsPage(driver);
        if (driver != null) {
            if(teamsPage.isTeamExists(teamName)){
                teamsPage.removeTeam(teamName);
            }
            driver.quit();
        }
    }
}
