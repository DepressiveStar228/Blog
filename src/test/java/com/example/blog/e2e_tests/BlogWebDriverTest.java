package com.example.blog.e2e_tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlogWebDriverTest {
    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private PostFormPage postFormPage;
    private PostDetailPage postDetailPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        postFormPage = new PostFormPage(driver);
        postDetailPage = new PostDetailPage(driver);
    }

    private void takeScreenshot(String stepName) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("target/screenshots/" + stepName + ".png");

            destFile.getParentFile().mkdirs();
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFullUserJourney() {
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@gmail.com";

        driver.get("http://localhost:8080/register");
        takeScreenshot("1_registration_page_opened");

        registerPage.fillRegistrationForm("TestUserQA", uniqueEmail, "111111");
        takeScreenshot("2_registration_form_filled");

        registerPage.submit();
        takeScreenshot("3_after_registration_submit");

        loginPage.loginAs(uniqueEmail, "111111");
        takeScreenshot("4_after_login_submit");

        assertTrue(driver.getPageSource().contains("Останні пости") || driver.getPageSource().contains("Вийти"),
                "Вхід не був успішним");

        homePage.clickNewPost();
        takeScreenshot("5_new_post_page_opened");

        postFormPage.createPost("Selenium E2E Post", "Це текст створений автоматично під час E2E тестування.");
        takeScreenshot("6_after_post_created");

        assertTrue(driver.getPageSource().contains("Selenium E2E Post"), "Пост не знайдено на сторінці");

        homePage.openFirstPost();
        takeScreenshot("7_post_detail_page_opened");

        postDetailPage.addComment("Чудовий пост! Скріншот працює.");
        takeScreenshot("8_after_comment_added");

        assertTrue(driver.getPageSource().contains("Чудовий пост! Скріншот працює."), "Коментар не з'явився");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}