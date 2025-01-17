package org.example.pages.team;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateTeamPage {
    private WebDriver driver;

    // Locators
    private By teamNameField = By.cssSelector("[data-testid='team-name']");
    private By continueButton = By.cssSelector("[data-testid='continue-button']");
    private By paragraphSelector = By.cssSelector("div[data-testid='field-error'] > p");
    private  By cancelButton= By.xpath("//button[text()='Cancel']");

    // Constructor
    public CreateTeamPage(WebDriver driver) {
        this.driver = driver;
    }

    // Set the team name
    public void setTeamName(String teamName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement teamNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(teamNameField));
        teamNameInput.clear();
        teamNameInput.sendKeys(teamName);
    }

    // Click the Continue button
    public void clickContinue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueBtn.click();
    }

    // Click the Continue button
    public void clickCancel() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        continueBtn.click();
    }

    // Method to get the text of the error message for exist team title
    public String getErrorMessageForExistTeamTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(paragraphSelector));
        return errorMessageElement.getText();
    }




}
