package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
import org.example.pages.team.TeamsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class RemoveTeamTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private String removeTeam ="removeTeam";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/auth/login");
        loginPage = new LoginPage(driver);

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser("solyma.mady@hotmail.co.il", "Admin123456789admin");

        EventTypesPage eventTypesPage = new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();
        TeamsPage teamsPage=new TeamsPage(driver);
        teamsPage.addTeam(removeTeam);
    }


    @Test
    public void removeTeamTest() throws InterruptedException {
        //Step1: navigate To Teams Page
        EventTypesPage eventTypesPage = new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();
        //remove team
        TeamsPage teamsPage=new TeamsPage(driver);
        Thread.sleep(1000);
        teamsPage.removeTeam(removeTeam);
        Thread.sleep(1000);
        assertTrue(!teamsPage.isTeamExists(removeTeam));

    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
