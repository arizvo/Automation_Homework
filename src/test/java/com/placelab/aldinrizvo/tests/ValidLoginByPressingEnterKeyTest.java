package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.pages.Homepage;
import com.placelab.aldinrizvo.pages.LoginPage;
import com.placelab.aldinrizvo.utils.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ValidLoginByPressingEnterKeyTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private Homepage homepage;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void setup(final String browser) {
        this.driver = WebDriverSetup.getWebDriver(browser);
        this.driver.get("https://demo.placelab.com/");
        this.loginPage = new LoginPage(driver);
        this.homepage = new Homepage(driver);
    }

    @Parameters({"email", "password"})
    @Test(priority = 2, groups = {"ValidLoginEnterKey", "Positive"})
    public void testValidLoginByEnterKey(final String email, final String password) {
        this.loginPage.validateLoginPageContent();
        this.loginPage.enterCredentials(email, password);
        this.loginPage.SubmitLoginButtonByEnterKey();

        final String expectedRole = "Group Admin";
        this.homepage.validateUserRole(expectedRole);

        // Logout
        this.homepage.signOut();

        // validate that user is signed out
        this.loginPage.validateLoginPageContent();
    }

    @AfterMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void teardown() {
        driver.close();
    }
}
