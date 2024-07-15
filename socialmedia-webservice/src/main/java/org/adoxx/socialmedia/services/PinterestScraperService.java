package org.adoxx.socialmedia.services;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void setup() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1300, 768));
    }

    @PreDestroy
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public boolean login() {
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
        try {
            driver.get("https://www.pinterest.com/pin/" + pinId);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            // Scroll a little to ensure the comments section can load -> add more if multiple comments are expected; Might need to adjust dynamically to scroll through the whole comment section
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)", "");

            // Wait a bit to give the page time to load comments
            TimeUnit.SECONDS.sleep(5);

            // Try clicking on the "2 Comments" section or its container to reveal the comments
            WebElement commentsSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[contains(text(), 'Comments')]/..")));
            // WebElement commentsSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[1]/div/div/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div[1]/div[2]/div/div[2]/div[2]/div[5]/div/div[2]/div/div/div[1]/div/div")));
            commentsSection.click();

            // Wait for comments to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("xuA")));

            // Fetch all comments
            List<WebElement> commentElements = driver.findElements(By.className("text-container"));

            // print the comments
            commentElements.forEach(comment -> System.out.println(comment.getText()));

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