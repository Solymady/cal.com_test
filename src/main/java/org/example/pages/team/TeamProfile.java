package org.example.pages.team;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TeamProfile {

    private WebDriver driver;
    By backButtonBy = By.xpath("//p[@class='min-h-4 max-w-36 truncate' and text()='Back']");

    public TeamProfile(WebDriver driver) {
        this.driver = driver;
    }

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(backButtonBy));
        backButton.click();
        System.out.println("Back button clicked successfully.");
    }
}
