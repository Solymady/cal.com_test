package org.example.pages;

import org.example.pages.team.TeamsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EventTypesPage {
    private WebDriver driver;

    private By pageHeaderBy = By.xpath("//h3[text()='Event Types']");
    private By createEventButtonBy = By.id("create-event");
    private By teamsLinkBy = By.cssSelector("[data-test-id='teams']");

    // URLs
    private String teamsPageUrl = "/teams";

    // Constructor
    public EventTypesPage(WebDriver driver) {
        this.driver = driver;
    }

    // Verify if the Event Types page is displayed
    public boolean isEventTypesPageDisplayed() {
        return driver.findElement(pageHeaderBy).isDisplayed();
    }

    // Get the page header text
    public String getPageHeaderText() {
        return driver.findElement(pageHeaderBy).getText();
    }

    // Click the "Create Event" button
    public void clickCreateEventButton() {
        driver.findElement(createEventButtonBy).click();
    }

    // Navigate to the Teams page
    public TeamsPage navigateToTeamsPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement teamsLink = wait.until(ExpectedConditions.elementToBeClickable(teamsLinkBy));
        teamsLink.click();
        wait.until(ExpectedConditions.urlContains(teamsPageUrl));

        return new TeamsPage(driver);
    }




}
