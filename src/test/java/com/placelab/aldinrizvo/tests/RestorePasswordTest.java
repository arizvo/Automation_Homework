package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.utilis.WebDriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class RestorePasswordTest {
    private WebDriver driver;

    @Parameters("browser")
    @BeforeTest
    public void setup(final String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
    }

    @Parameters("email")
    @Test
    public void testPasswordRestore(final String email) {
        // Validate page title is correct
        final String actualPageTitle = driver.getTitle();
        final String expectedPageTitle = "PlaceLab";
        Assert.assertEquals(actualPageTitle, expectedPageTitle);

        // Validate header is displayed
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

        // Validate that link for restoring forgotten password is displayed
        driver.findElement(By.xpath("//a[@href ='/password/forgot']")).click();

        // Validate email field is displayed
        Assert.assertTrue(
                driver.findElement(By.id("login_form")).isDisplayed(), "Validate login form is displayed"
        );
        Assert.assertTrue(
                driver.findElement(By.id("email")).isDisplayed(), "Validate email field is displayed"
        );

        // Enter valid email
        driver.findElement(By.id("email")).sendKeys(email);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Submit
        driver.findElement(By.xpath("//input[@name='commit']")).click();

        // Validate email with password reset instructions is sent
        final String expectedEmailSentMessage = "We have sent you a link to change your password";
        final String actualEmailSentMessage = driver.findElement(By.cssSelector("div#login > p.bold")).getText();
        Assert.assertEquals(actualEmailSentMessage, expectedEmailSentMessage);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterTest
    public void teardown() {
        driver.close();
    }
}
