package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
import org.example.pages.team.AddTeamMembersPage;
import org.example.pages.team.CreateTeamPage;
import org.example.pages.team.TeamsPage;
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

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTeamMemberTest {
    private WebDriver driver;
    private TeamsPage teamsPage;
    private EventTypesPage eventTypesPage;
    private String teamName = "testAddMember";
    private CreateTeamPage createTeamPage;


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
        Thread.sleep(5000);
    }

    @Test
    public void testAddNewTeam() throws InterruptedException {
        // Step 1: Navigate to Teams Page
        eventTypesPage = new EventTypesPage(driver);
        Thread.sleep(5000);
        eventTypesPage.navigateToTeamsPage();

        // Step 2: Click Add New Team
        teamsPage = new TeamsPage(driver);
        Thread.sleep(1000);
        teamsPage.clickAddNewTeam();

        // Step 3: Create a new team
        CreateTeamPage createTeamPage = new CreateTeamPage(driver);
        createTeamPage.setTeamName(teamName);
        createTeamPage.clickContinue();

        // Step 4: Add team
        AddTeamMembersPage addTeamMembersPage = new AddTeamMembersPage(driver);

        // Add Member 1
        addTeamMembersPage.addMember("member1@gmail.com", "Admin");
        Thread.sleep(1000);

    }


    @AfterEach
    public void tearDown() throws InterruptedException {
        driver.navigate().back();
        Thread.sleep(1000);
        driver.navigate().back();
        Thread.sleep(1000);
        if (driver != null) {
            Thread.sleep(1000);
            if(teamsPage.isTeamExists(teamName)){
                Thread.sleep(2000);
                teamsPage.removeTeam(teamName);
            }
            Thread.sleep(2000);
            driver.quit();
        }
    }
}
