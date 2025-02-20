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
    private By teamsLinkBy = By.cssSelector("a[data-test-id=\"teams\"][aria-label=\"Teams\"][href=\"/teams\"]");

    // URLs
    private String teamsPageUrl = "/teams";


    // Constructor with page validation
    public EventTypesPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        Thread.sleep(3000);
        if(isEventTypesPageDisplayed()){
            System.out.println("in event page");
        }else{
            System.out.println("not in event page");
        }
    }

    private boolean isOnEventTypesPage() {
        // Locate the header element
        WebElement header = driver.findElement(pageHeaderBy);
        // Validate header text
        return header.getText().equals("Event Types"); // Replace with actual header text
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
    public TeamsPage navigateToTeamsPage() throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement teamsLink = wait.until(ExpectedConditions.elementToBeClickable(teamsLinkBy));
        teamsLink.click();
        wait.until(ExpectedConditions.urlContains(teamsPageUrl));

        return new TeamsPage(driver);
    }




}
