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
    private boolean loggedIn = false;

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${pinterest.email}")
    private String email;

    @Value("${pinterest.password}")
    private String password;

    private void initializeWebDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", System.getenv("WEB_DRIVER_PATH"));

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");  // Important for Docker
            options.addArguments("--disable-dev-shm-usage");
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
        String[] xpaths = {
                "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[1]/div[2]/div/div[2]/div[2]/div[5]/div/div/div/div/div[2]/div",
                "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[1]/div[2]",
                "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[1]/div[2]/div",
                "/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]"
        };

        for (String xpath : xpaths) {
            try {
                // Get the total number of comments
                WebElement commentsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Comments')]/..")));
                String commentsText = commentsHeader.getText();
                int totalComments = Integer.parseInt(commentsText.replaceAll("\\D+", ""));
                log.info("Total comments: {}", totalComments);

                int loadedComments = 0;
                int previousLoadedComments = -1;
                List<WebElement> commentElements = null;
                WebElement commentsBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

                while (loadedComments < totalComments) {
                    // Scroll within the comments box to load more comments
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false)", commentsBox);
                    TimeUnit.SECONDS.sleep(2); // Wait for comments to load

                    // Update the number of loaded comments
                    commentElements = driver.findElements(By.className("text-container"));
                    loadedComments = commentElements.size();
                    log.info("Loaded comments: {}", loadedComments);

                    // Break the loop if loaded comments count doesn't change
                    if (loadedComments == previousLoadedComments) {
                        break;
                    }
                    previousLoadedComments = loadedComments;
                }
                return commentElements;
            } catch (Exception e) {
                log.error("Error with XPath: " + xpath, e);
            }
        }

        log.error("All XPath attempts failed to load comments");
        return List.of();
    }

    public List<String> fetchComments(String pinId) {
        if (!login()) {
            log.error("Login failed, cannot fetch comments");
            return List.of();
        }

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
}