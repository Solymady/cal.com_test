package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessageBy = By.xpath("//h3[text()='Email or password is incorrect.']"); // Locator for error message


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
    public EventTypesPage loginAsValidUser(String userName, String password) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(userName);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();

        // Navigate to the Event Types page on successful login
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
        return driver.findElement(errorMessageBy).isDisplayed();
    }

    // Get the text of the error message
    public String getErrorMessageText() {
        return driver.findElement(errorMessageBy).getText();
    }


    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
