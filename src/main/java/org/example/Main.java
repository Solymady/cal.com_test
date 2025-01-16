package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    private static final String baseURL = "http://localhost:8082";

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get(baseURL);

        String pageTitle = driver.getTitle();

        WebElement libraryLink = driver.findElement(By.cssSelector("a[href='/library']"));
        libraryLink.click();

        WebElement pageMessage = driver.findElement(By.cssSelector("div.content h1"));
        String pageMessageText = pageMessage.getText();

        driver.quit();
    }
}