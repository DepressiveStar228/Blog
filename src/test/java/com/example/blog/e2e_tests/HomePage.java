package com.example.blog.e2e_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(xpath = "//a[contains(text(), '+ Новий пост')]")
    private WebElement newPostButton;

    @FindBy(css = ".post-card h2 a")
    private WebElement firstPostLink;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickNewPost() {
        newPostButton.click();
    }

    public void openFirstPost() {
        firstPostLink.click();
    }
}