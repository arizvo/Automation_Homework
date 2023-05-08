package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.utilis.WebDriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ValidLoginTest {
    private WebDriver driver;

    @Parameters("browser")
    @BeforeTest
    public void setup(final String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
    }

    @Parameters({"email", "password"})
    @Test
    public void testValidLogin(final String email, final String password) {
        // validate page title is correct
        final String actualPageTitle = driver.getTitle();
        final String expectedPageTitle = "PlaceLab";
        Assert.assertEquals(actualPageTitle, expectedPageTitle);

        // validate header is displayed
        final boolean isHeaderDisplayed = driver.findElement(By.cssSelector("div#login > p.headline")).isDisplayed();
        Assert.assertTrue(isHeaderDisplayed);

        // Validate login form is displayed
        Assert.assertTrue(
                driver.findElement(By.id("login_form")).isDisplayed(), "Validate login form is displayed."
        );
        Assert.assertTrue(
                driver.findElement(By.id("email")).isDisplayed(), "Validate email field is displayed."
        );
        Assert.assertTrue(
                driver.findElement(By.id("password")).isDisplayed(), "Validate password field is displayed."
        );

        // Enter email
        driver.findElement(By.id("email")).sendKeys(email);

        // Enter password
        driver.findElement(By.id("password")).sendKeys(password);

        // Submit
        driver.findElement(By.xpath("//input[@name='commit']")).click();

        final String expectedRole = "Group Admin";
        final String actualRole = driver.findElement(By.id("user-role")).getText();
        Assert.assertEquals(actualRole, expectedRole, "Validate user role for logged in user.");

        // Logout
        driver.findElement(By.id("user-role")).click();
        Assert.assertTrue(
                driver.findElement(By.linkText("Sign out")).isDisplayed(), "Validate is Sign out button displayed"
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.linkText("Sign out")).click();

        // validate that user is signed out
        Assert.assertTrue(
                driver.findElement(By.id("login_form")).isDisplayed(), "Validate login form is displayed."
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterTest
    public void teardown() {
        driver.close();
    }
}
