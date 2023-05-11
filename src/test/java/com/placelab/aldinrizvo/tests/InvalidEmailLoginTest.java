package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.pages.LoginPage;
import com.placelab.aldinrizvo.utils.WebDriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.UUID;

public class InvalidEmailLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    final private String invalidEmail = UUID.randomUUID().toString();

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void setup(final String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
        this.loginPage = new LoginPage(driver);
    }

    @Parameters("password")
    @Test(priority = 3, groups = {"InvalidEmailLogin", "Negative"})
    public void testInvalidEmailLogin(final String password) {
        loginPage.validateLoginPageContent();
        loginPage.enterCredentials(invalidEmail, password);
        loginPage.clickSubmitLoginButton();
        loginPage.validateLoginErrorMessage();
    }

    @AfterMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void teardown() {
        driver.close();
    }
}
