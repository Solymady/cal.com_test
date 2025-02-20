package org.example.pages.team;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddTeamEventPage {
    private WebDriver driver;

    // Locators
    private By titleFieldBy = By.xpath("//input[@placeholder='Quick Chat']");
    private By collectiveOptionBy = By.xpath("//label[contains(text(), 'Collective')]");
    private By roundRobinOptionBy = By.xpath("//label[contains(text(), 'Round Robin')]");
    private By managedEventOptionBy = By.xpath("//label[contains(text(), 'Managed Event')]");
    private By finishButtonBy = By.xpath("//button[contains(text(), 'Finish')]");
    private By doThisLaterButtonBy = By.xpath("//button[contains(text(), 'I’ll do this later')]");

    // Constructor
    public AddTeamEventPage(WebDriver driver) {
        this.driver = driver;
    }

    // Enter the title for the event
    public void enterTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleField = wait.until(ExpectedConditions.visibilityOfElementLocated(titleFieldBy));
        titleField.clear();
        titleField.sendKeys(title);
    }


    public void selectCollectiveOption() {

        driver.findElement(collectiveOptionBy).click();
    }

    // Select the "Round Robin" assignment option
    public void selectRoundRobinOption() {

        driver.findElement(roundRobinOptionBy).click();
    }

    // Select the "Managed Event" assignment option
    public void selectManagedEventOption() {

        driver.findElement(managedEventOptionBy).click();
    }

    // Click the "Finish" button
    public void clickFinish() {

        driver.findElement(finishButtonBy).click();
    }

    // Click the "I'll do this later" button
    public void clickDoThisLater() {

        driver.findElement(doThisLaterButtonBy).click();
    }

    // Combined method to create an event type
    public void createEventType(String title, String url, String assignmentType) {
        enterTitle(title);
        switch (assignmentType.toLowerCase()) {
            case "collective":
                selectCollectiveOption();
                break;
            case "round robin":
                selectRoundRobinOption();
                break;
            case "managed event":
                selectManagedEventOption();
                break;
            default:
                throw new IllegalArgumentException("Invalid assignment type: " + assignmentType);
        }
        clickFinish();
    }
}
