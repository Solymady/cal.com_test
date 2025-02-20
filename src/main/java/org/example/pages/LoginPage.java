package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessageBy = By.xpath("//h3[text()='Email or password is incorrect.']"); // Locator for error message
    private By EventTypePage= By.xpath("//h3[text()='Event Types']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterEmail(String email) {
        WebElement emailElement = driver.findElement(emailField);
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    // Perform login as a valid user
    public EventTypesPage loginAsValidUser(String userName, String password) throws InterruptedException {
        // Clear and enter email
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(userName);

        // Clear and enter password
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);

        // Click the login button
        driver.findElement(loginButton).click();

        // Explicitly wait for the Event Types page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/event-types"));
        wait.until(ExpectedConditions.presenceOfElementLocated(EventTypePage));

        // Return a new EventTypesPage object if successfully navigated
        return new EventTypesPage(driver);
    }


    public LoginPage loginWithInvalidUser(String userName, String password) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(userName);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return this;
    }

    // Check if the error message is displayed
    public boolean isErrorMessageDisplayed() {
        // Wait for the error message to appear and return true if found
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(errorMessageBy)).isDisplayed();
    }


}
