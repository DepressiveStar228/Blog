package com.example.blog.e2e_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PostFormPage {
    @FindBy(id = "title")
    private WebElement titleInput;

    @FindBy(id = "content")
    private WebElement contentInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    public PostFormPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createPost(String title, String content) {
        titleInput.sendKeys(title);
        contentInput.sendKeys(content);
        submitButton.click();
    }
}