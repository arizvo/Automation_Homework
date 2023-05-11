package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.pages.LoginPage;
import com.placelab.aldinrizvo.utils.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Random;

public class InvalidPasswordLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    private final String invalidPassword = new Random().nextInt(9999999) + "";

    @Parameters("browser")
    @BeforeMethod
    public void setup(final String browser) {
        this.driver = WebDriverSetup.getWebDriver(browser);
        this.driver.get("https://demo.placelab.com/");
        this.loginPage = new LoginPage(driver);
    }

    @Parameters("email")
    @Test(priority = 4, groups = {"InvalidPasswordLogin", "Negative"})
    public void testInvalidPasswordLogin(final String email) {
        this.loginPage.validateLoginPageContent();
        this.loginPage.enterCredentials(email, invalidPassword);
        this.loginPage.clickSubmitLoginButton();
        this.loginPage.validateLoginErrorMessage();
    }

    @AfterMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void teardown() {
        driver.close();
    }
}
