package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.pages.Homepage;
import com.placelab.aldinrizvo.pages.LoginPage;
import com.placelab.aldinrizvo.utils.WebDriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ValidLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private Homepage homepage;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void setup(final String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
        this.loginPage = new LoginPage(driver);
        this.homepage = new Homepage(driver);
    }

    @Parameters({"email", "password"})
    @Test(priority = 1, groups = {"ValidLogin", "Positive"})
    public void testValidLogin(final String email, final String password) {
        loginPage.validateLoginPageContent();
        loginPage.enterCredentials(email, password);
        loginPage.clickSubmitLoginButton();

        final String expectedRole = "Group Admin";
        homepage.validateUserRole(expectedRole);

        // Logout
        homepage.signOut();

        // validate that user is signed out
        loginPage.validateLoginPageContent();
    }

    @AfterMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void teardown() {
        driver.close();
    }
}
