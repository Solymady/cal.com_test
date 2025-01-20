package org.example.pages.team;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class AddTeamMembersPage {
    private WebDriver driver;

    // Locators
    private By addTeamMemberButtonBy = By.cssSelector("[data-testid='new-member-button']");
    private By emailFieldBy = By.cssSelector("#inviteUser");
    private By dropdownBy = By.cssSelector("span[data-testid='select-control']");
    private By sendInviteButtonBy = By.cssSelector("button[data-testid='invite-new-member-button']");
    private By continueButtonBy =By.cssSelector("[data-testid='publish-button']");
    private By memberSelector = By.id("react-select-2-option-0");
    private By adminSelector = By.id("react-select-2-option-1");
    private By ownerSelector = By.id("react-select-2-option-2");
    private By SuccessMessageAddTeamMember=By.cssSelector("p[data-testid='toast-success']");
    private By memberExistMessage=By.xpath("//span[text()='Member has already been invited']");




    // Constructor
    public AddTeamMembersPage(WebDriver driver) {
        this.driver = driver;
    }




    public void enterEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailFieldBy));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public String getSuccessMessageAddTeamMember() {
        // Create WebDriverWait instance (e.g., wait up to 10 seconds)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait until the success message element is visible
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(SuccessMessageAddTeamMember));
        // Return the text of the success message
        return successMessage.getText();
    }

    // Method to get the text of the error message for exist team title
    public String getErrorMessageForExistTeamMember() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(memberExistMessage));
        return errorMessageElement.getText();
    }


    public Boolean selectRole(String option) throws InterruptedException {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // Wait for the dropdown to be clickable and click it
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownBy));
        actions.moveToElement(dropdown).click().perform();

        // Convert the option to lowercase
        option = option.toLowerCase();
        By optionBy;

        // Determine the correct selector based on the option
        if (option.equals("admin")) {
            optionBy = adminSelector;
            WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionBy));
            actions.moveToElement(optionElement).click().perform();
        } else if (option.equals("member")) {

        } else if (option.equals("owner")) {
            optionBy = ownerSelector;
            WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionBy));
            actions.moveToElement(optionElement).click().perform();
        } else {
            return false; // Return false if the option doesn't match any valid values
        }
        return true;
    }

    // Click "Send Invite" button
    public void clickSendInvite() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sendInviteButton = wait.until(ExpectedConditions.elementToBeClickable(sendInviteButtonBy));
        sendInviteButton.click();
    }

    // Click "Continue" button to proceed
    public void clickContinue() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueButtonBy));
        Thread.sleep(1000);
        continueButton.click();
    }

    public void clickAddTeamMember() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement addTeamMemberButton = wait.until(ExpectedConditions.elementToBeClickable(addTeamMemberButtonBy));
        WebElement hoverable = driver.findElement(addTeamMemberButtonBy);
        new Actions(driver)
                .moveToElement(hoverable)
                .click(hoverable).perform();
    }

    public void addMember(String email,String role) throws InterruptedException {
        Thread.sleep(1000);
        clickAddTeamMember();
        enterEmail(email);
        selectRole(role);
        clickSendInvite();
    }


}
