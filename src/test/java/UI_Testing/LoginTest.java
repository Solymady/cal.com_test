package UI_Testing;

import org.example.pages.EventTypesPage;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/auth/login");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testEventTypesPageHeader() {
        EventTypesPage eventTypesPage = loginPage.loginAsValidUser("solyma.mady@hotmail.co.il", "Solyma315087817");

        // Wait for login
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement eventTypesHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3[text()='Event Types']")));

        assertTrue(eventTypesHeader.isDisplayed());
    }


    @Test
    public void testInvalidLogin() {
        // Perform login with invalid credentials
        loginPage.loginWithInvalidUser("invalid@example.com", "wrongpassword");

        // Wait for error message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h3[contains(text(), 'Email or password is incorrect')]")));

        // Verify the error message is displayed
        assertTrue(errorMessage.isDisplayed(), "Error message is not displayed.");

        // Verify the error message text
        String actualText = errorMessage.getText();
        String expectedText = "Email or password is incorrect.";
        assertEquals(expectedText, actualText);
    }



    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
