package org.adoxx.socialmedia.services;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PinterestScraperService {

    private WebDriver driver;

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${pinterest.email}")
    private String email;

    @Value("${pinterest.password}")
    private String password;

    private void initializeWebDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1300,760");

            driver = new ChromeDriver(options);
        }
    }

    @PreDestroy
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public boolean login() {

        initializeWebDriver();

        try {
            driver.get("https://www.pinterest.com/login/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
            emailField.sendKeys(email);
            passwordField.sendKeys(password);
            loginButton.click();

            // Add a simple check to confirm login
            TimeUnit.SECONDS.sleep(10);  // Wait for login to process
            return driver.getCurrentUrl().contains("https://www.pinterest.com/");
        } catch (Exception e) {
            log.error("Login failed", e);
            return false;
        }
    }

    public List<String> fetchComments(String pinId) {
        // Ensure the user is logged in before fetching comments
        login();

        try {
            driver.get("https://www.pinterest.com/pin/" + pinId);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            // Scroll a little to ensure the comments section can load
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");

            // Wait a bit to give the page time to load comments
            TimeUnit.SECONDS.sleep(5);

            // Check if comments are already visible
            List<WebElement> visibleComments = driver.findElements(By.className("xuA"));
            if (visibleComments.isEmpty()) {
                // Try clicking on the "Comments" section or its container to reveal the comments
                WebElement commentsSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[contains(text(), 'Comments')]/..")));
                commentsSection.click();

                // Wait for comments to be visible
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("xuA")));
            }

            // Fetch all comments
            List<WebElement> commentElements = driver.findElements(By.className("text-container"));

            return commentElements.stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.isEmpty())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Fetching comments failed", e);
            return List.of();
        }
    }




}