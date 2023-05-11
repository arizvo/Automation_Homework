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

import java.util.Random;

public class InvalidPasswordLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    final private String invalidPassword = new Random().nextInt(9999999) + "";

    @Parameters("browser")
    @BeforeMethod
    public void setup(final String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
        this.loginPage = new LoginPage(driver);
    }

    @Parameters("email")
    @Test(priority = 4, groups = {"InvalidPasswordLogin", "Negative"})
    public void testInvalidPasswordLogin(final String email) {
        loginPage.validateLoginPageContent();
        loginPage.enterCredentials(email, invalidPassword);
        loginPage.clickSubmitLoginButton();
        loginPage.validateLoginErrorMessage();
    }

    @AfterMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void teardown() {
        driver.close();
    }
}
