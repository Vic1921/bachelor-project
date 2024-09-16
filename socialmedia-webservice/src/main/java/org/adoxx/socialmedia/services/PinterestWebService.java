package org.adoxx.socialmedia.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.openqa.selenium.interactions.WheelInput.ScrollOrigin;

import jakarta.annotation.PreDestroy;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PinterestWebService {

    private WebDriver driver;
    private boolean loggedIn = false;

    @Value("${pinterest.email}")
    private String email;

    @Value("${pinterest.password}")
    private String password;

    private void initializeWebDriver() {
        log.info("Initializing WebDriver");
        try {
            if (driver == null) {
                log.info("Using system-installed ChromeDriver");
                System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=2300,1352");

                driver = new ChromeDriver(options);

                log.info("WebDriver initialized successfully");
            }
        } catch (Exception e) {
            log.error("Failed to initialize WebDriver", e);
        }
    }


    @PreDestroy
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public boolean login() {
        if (loggedIn) {
            return true;
        }

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
            loggedIn = true;
            return true;
        } catch (Exception e) {
            log.error("Login failed", e);
            return false;
        }
    }


    private List<WebElement> scrollUntilAllCommentsLoaded(WebDriver driver, WebDriverWait wait) {
        log.info("Scrolling to load all comments");
        String absoluteXpath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[1]/div[2]";

        try {
            // Get the total number of comments
            WebElement commentsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(), 'Comments')]/..")));
            String commentsText = commentsHeader.getText();
            int totalComments = Integer.parseInt(commentsText.replaceAll("\\D+", ""));
            int targetComments = totalComments * 2; // Because the loadedComments get doubled
            log.info("Total comments: {}", totalComments);

            int loadedComments = 0;
            List<WebElement> commentElements = null;
            WebElement commentsBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(absoluteXpath)));

            // Create an instance of Actions
            Actions actions = new Actions(driver);

            // Focus on the comments box
            actions.moveToElement(commentsBox).click().perform();

            while (loadedComments < totalComments*2) {
                // Scroll within the comments box by 500 pixels
                actions.scrollFromOrigin(ScrollOrigin.fromElement(commentsBox), 0, 500).perform();

                TimeUnit.SECONDS.sleep(1); // Wait for comments to load

                // Update the number of loaded comments
                commentElements = driver.findElements(By.className("text-container"));
                loadedComments = commentElements.size();
                log.info("Loaded comments: {}", loadedComments);
            }
            return commentElements;
        } catch (Exception e) {
            log.error("Error while loading comments", e);
        }

        log.error("Failed to load comments");
        return List.of();
    }


    public List<String> fetchComments(String pinId) {
        if (!login()) {
            log.error("Login failed, cannot fetch comments");
            return List.of();
        }

        log.info("Fetching comments for pin {} this might take a while as the webdriver is downloaded and started", pinId);

        try {
            driver.get("https://www.pinterest.com/pin/" + pinId);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            // Scroll a little to ensure the comments section can load
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)", "");

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

            // Scroll dynamically until all comments are loaded and return the list of comments
            List<WebElement> commentElements = scrollUntilAllCommentsLoaded(driver, wait);

            // Fetch all comments
            return commentElements.stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.isEmpty())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Fetching comments failed", e);
            return List.of();
        }
    }

    // Method to post the result of the analysis as a comment
    public void postComment(String pinId, String comment) {
        if (!login()) {
            log.error("Login failed, cannot post comment");
            return;
        }

        String commentSectionXPath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div/div/div/div/div[1]/div/div/div/div/div/div/div[2]/div";
        String postButtonXPath = "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/button";

        try {
            driver.get("https://www.pinterest.com/pin/" + pinId);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            // Scroll a little to ensure the comments section can load
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");

            // Wait a bit to give the page time to load comments
            TimeUnit.SECONDS.sleep(5);

            // Scroll to see the comment section a bit better
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");

            // Find the comment box and post the comment
            WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(commentSectionXPath)));
            commentBox.click();
            commentBox.sendKeys(comment);

            // Find the submit button and click it
            WebElement postButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(postButtonXPath)));
            postButton.click();
        } catch (Exception e) {
            log.error("Posting comment failed", e);
        }
    }
}
