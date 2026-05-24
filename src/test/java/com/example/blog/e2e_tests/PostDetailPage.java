package com.example.blog.e2e_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PostDetailPage {
    @FindBy(name = "content")
    private WebElement commentInput;

    @FindBy(xpath = "//button[contains(text(), 'Надіслати')]")
    private WebElement submitCommentButton;

    public PostDetailPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addComment(String text) {
        commentInput.sendKeys(text);
        submitCommentButton.click();
    }
}