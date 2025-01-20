package org.example.pages.team;

import org.apache.commons.io.FileUtils;
import org.example.pages.EventTypesPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TeamsPage {
    private WebDriver driver;

    // Locators
    private By addNewTeamButtonBy = By.xpath("//a[@data-testid=\"new-team-btn\"]");
    private By teamListBy = By.xpath("//div[contains(@class, 'team-list-item')]");
    private By disbandTeamBy = By.xpath("//div[@role='menuitem']//div[text()='Disband Team']");
    private By confirmButtonBy = By.xpath("//button[@data-testid='dialog-confirmation']");
    private By ellipsisButtonBy = By.cssSelector("button#radix-\\:rdh\\:");
    private By pageHeaderBy = By.xpath("//h3[text()='Teams']");


    // Constructor
    public TeamsPage(WebDriver driver) {
        this.driver = driver;
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeaderBy));
//        if (!headerElement.isDisplayed()) {
//            throw new IllegalStateException("This is not the Teams page. Current URL: " + driver.getCurrentUrl());
//        }
    }

    // Verify if the Teams page is displayed
    public boolean isTeamsPageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement headerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeaderBy));
        return headerElement.isDisplayed();
    }



    // Click the "Add New Team" button
    public void clickAddNewTeam() throws InterruptedException {
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addNewTeam = wait.until(ExpectedConditions.elementToBeClickable(addNewTeamButtonBy));
        addNewTeam.click();
    }



    public void clickEllipsisButtonForTeam(String teamName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Dynamic XPath with variable teamName
        String xpath = "//li[.//span[text()='" + teamName + "']]//button[contains(@class, 'rounded-md') and contains(@class, 'text-emphasis') and @aria-haspopup='menu' and @data-state='closed']";
        // Locate the button using the dynamic XPath
        By ellipsisButtonLocator = By.xpath(xpath);
        // Wait for the button to be clickable
        WebElement ellipsisButton = wait.until(ExpectedConditions.elementToBeClickable(ellipsisButtonLocator));
        // Click the ellipsis button
        ellipsisButton.click();
    }

    // Check if team exists in any <ul> on the page
    public boolean isTeamExists(String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Wait for all <ul> elements to be visible
            List<WebElement> ulElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("ul")));

            // Iterate through each <ul>
            for (WebElement ul : ulElements) {
                // Locate all <li> elements within the current <ul>
                List<WebElement> liElements = ul.findElements(By.tagName("li"));

                // Iterate through each <li>
                for (WebElement li : liElements) {
                    // Check if the <li> contains a <span> with the desired class and text
                    List<WebElement> spanElements = li.findElements(By.cssSelector("span.text-default.text-sm.font-bold"));

                    // If the <span> exists, check its text
                    if (!spanElements.isEmpty() && spanElements.get(0).getText().equals(expectedText)) {
                        System.out.println("Found matching team: " + expectedText);
                        return true; // Return true if a match is found
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No <ul> elements found on the page.");
        }

        System.out.println("No matching team found: " + expectedText);
        return false; // Return false if no match is found or no <ul>/<li> elements exist
    }


    // Remove team member by name
    public boolean removeTeam(String name) throws InterruptedException {
        boolean isTeamExists=isTeamExists(name);

        if (isTeamExists) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            clickEllipsisButtonForTeam(name);


            WebElement disbandButton = wait.until(ExpectedConditions.elementToBeClickable(disbandTeamBy));
            Thread.sleep(1000);
            disbandButton.click();


            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmButtonBy));
            Thread.sleep(1000);
            confirmButton.click();
            return true;
        }
        return false;
    }

    public void addTeam(String teamName) throws InterruptedException {
        clickAddNewTeam();
        CreateTeamPage createTeamPage= new CreateTeamPage(driver);
        createTeamPage.setTeamName(teamName);
        createTeamPage.clickContinue();
        AddTeamMembersPage addTeamMembersPage=new AddTeamMembersPage(driver);
        addTeamMembersPage.clickContinue();
        AddNewTeamEvent addNewTeamEvent=new AddNewTeamEvent(driver);
        addNewTeamEvent.clickdoLaterButton();
        TeamProfile teamProfile=new TeamProfile(driver);
        teamProfile.clickBackButton();
        Thread.sleep(2000);
    }


}
