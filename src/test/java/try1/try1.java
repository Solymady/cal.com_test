package try1;

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

public class try1 {
    private WebDriver driver;
    private TeamsPage teamsPage;
    private EventTypesPage eventTypesPage;
    private String teamName = "testAddMember";
    private CreateTeamPage createTeamPage;
    AddTeamMembersPage addTeamMembersPage;


    @BeforeEach
    public void setUp() throws MalformedURLException, InterruptedException {
        // Initialize WebDriver
        driver = getDriver();


        driver.manage().window().maximize();
        driver.get("https://397e-2a06-c701-7aa2-8800-e8d6-ed49-b4e-cd59.ngrok-free.app");

        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            System.out.println("Ngrok warning page was not loaded");
        }


        eventTypesPage = new EventTypesPage(driver);
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            System.out.println("Driver is null");
        }
        teamsPage = new TeamsPage(driver);
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            System.out.println("Driver is null");
        }
        createTeamPage= new CreateTeamPage(driver);
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            System.out.println("Driver is null");
        }
        addTeamMembersPage= new AddTeamMembersPage(driver);
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            System.out.println("Driver is null");
        }

        // Login
        LoginPage loginPage = new LoginPage(driver);
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            System.out.println("Driver is null");
        }
        loginPage.loginAsValidUser("solyma.mady@hotmail.co.il", "Admin123456789admin");

        // Navigate to Teams page
        teamsPage = new TeamsPage(driver);

        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else {
            System.out.println("Driver is null");
        }
    }

    @Test
    public void testAddNewTeam() throws InterruptedException {
        // Step 1: Navigate to Teams Page
        eventTypesPage = new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();

        // Step 2: Click Add New Team
        teamsPage = new TeamsPage(driver);
        teamsPage.clickAddNewTeam();

        // Step 3: Create a new team
        createTeamPage = new CreateTeamPage(driver);
        createTeamPage.setTeamName(teamName);
        createTeamPage.clickContinue();

        // Step 4: Add team
        addTeamMembersPage = new AddTeamMembersPage(driver);

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
                teamsPage.removeTeam(teamName);
            }
            driver.quit();
        }
    }
}
