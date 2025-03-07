package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() throws InterruptedException {
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

        Thread.sleep(1000);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testvalidLogin() throws InterruptedException {
        EventTypesPage eventTypesPage = loginPage.loginAsValidUser(TestData.VALID_EMAIL, TestData.VALID_PASSWORD);
        // Wait for login
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement eventTypesHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Event Types']")));
        Thread.sleep(5000);
        assertTrue(eventTypesHeader.isDisplayed());
    }


    @Test
    public void testInvalidLogin() {
        // Perform login with invalid credentials
        loginPage.loginWithInvalidUser(TestData.INVALID_EMAIL, TestData.INVALID_PASSWORD);
        //LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isErrorMessageDisplayed());
    }



    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
