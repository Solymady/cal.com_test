package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class DriverFactory {

    private static final String grid_url = System.getenv("GRID_URL");
    private static final String browser = Optional
            .ofNullable(System.getenv("BROWSER"))
            .orElse("chrome");

    public static WebDriver getDriver() {
        System.out.println("Grid URL: " + grid_url);
        System.out.println("Browser: " + browser);

        if (grid_url != null) {
            return getRemoteDriver(browser);
        } else {
            return getLocalDriver(browser);
        }
    }

    private static WebDriver getRemoteDriver(String browser) {
        URL hubUrl;
        try {
            hubUrl = new URI(grid_url).toURL();
        } catch (URISyntaxException | MalformedURLException err) {
            throw new IllegalArgumentException("Invalid grid URL: " + grid_url, err);
        }

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
                return new RemoteWebDriver(hubUrl, chromeOptions);

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-headless");
                return new RemoteWebDriver(hubUrl, firefoxOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver getLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();

            case "firefox":
                return new FirefoxDriver();

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
