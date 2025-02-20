package org.example.pages.team;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddNewTeamEvent {

    private WebDriver driver;

    // Locators
    private By titleFieldBy = By.name("title");
    private By finishButtonBy = By.cssSelector("button[data-testid='finish-button']");
    private By handledoButtonBy = By.cssSelector("button[data-testid='handle-later-button']");

    // Constructor
    public AddNewTeamEvent(WebDriver driver) {

        this.driver = driver;
    }

    // Enter the title for the event
    public void enterTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(titleFieldBy));
        titleField.clear();
        titleField.sendKeys(title);
    }

    // Method to select an event type
    public void selectEventType(String eventType) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Dynamic XPath to locate the button based on the event type value
        String xpath = "//button[@role='radio' and @value='" + eventType + "']";

        // Wait for the button to be clickable and click it
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        button.click();
    }

    // Method to press the finish button
    public void clickFinishButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(finishButtonBy));
        finishButton.click();
    }

    // Method to press the "I'll do this later" button
    public void clickdoLaterButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement handleLaterButton = wait.until(ExpectedConditions.elementToBeClickable(handledoButtonBy));
        handleLaterButton.click();
    }

    public void fillNewTeamEvent(String EventTitle, String EventType){
        enterTitle(EventTitle);
        selectEventType(EventType);
        clickFinishButton();
    }

}
