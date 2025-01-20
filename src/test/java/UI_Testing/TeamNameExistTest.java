package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
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

import java.time.Duration;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamNameExistTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private String testExist = "testExist";

    @BeforeEach
    public void setUp() throws InterruptedException {
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

        EventTypesPage eventTypesPage = new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();
        TeamsPage teamsPage=new TeamsPage(driver);
        teamsPage.addTeam(testExist);
    }

    @Test
    public void testExistTeamTitle() throws InterruptedException {
        //Step1: navigate To Teams Page
        EventTypesPage eventTypesPage=new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();

        // Step 2: Click Add New Team
        TeamsPage teamsPage=new TeamsPage(driver);
        teamsPage.clickAddNewTeam();

        // Step 3: Create a new team
        CreateTeamPage createTeamPage = new CreateTeamPage(driver);
        createTeamPage.setTeamName(testExist);
        createTeamPage.clickContinue();
        assertEquals("This URL is already taken",createTeamPage.getErrorMessageForExistTeamTitle());
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            CreateTeamPage createTeamPage=new CreateTeamPage(driver);
            createTeamPage.clickCancel();
            TeamsPage teamsPage=new TeamsPage(driver);
            Thread.sleep(1000);
            teamsPage.removeTeam(testExist);
            Thread.sleep(2000);
            driver.quit();
        }
    }
}
