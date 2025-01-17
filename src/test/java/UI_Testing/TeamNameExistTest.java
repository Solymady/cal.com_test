package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
import org.example.pages.team.CreateTeamPage;
import org.example.pages.team.TeamsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamNameExistTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private String testExist = "testExist";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/auth/login");
        loginPage = new LoginPage(driver);

        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsValidUser("solyma.mady@hotmail.co.il", "Solyma315087817");

        EventTypesPage eventTypesPage = new EventTypesPage(driver);
        eventTypesPage.navigateToTeamsPage();
        TeamsPage teamsPage=new TeamsPage(driver);
        teamsPage.addTeam(testExist);
    }

    @Test
    public void testExistTeamTitle(){
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
    public void tearDown() {
        if (driver != null) {
            CreateTeamPage createTeamPage=new CreateTeamPage(driver);
            createTeamPage.clickCancel();
            TeamsPage teamsPage=new TeamsPage(driver);
            teamsPage.removeTeam(testExist);
            driver.quit();
        }
    }
}
