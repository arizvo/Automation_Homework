package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {
    private static final By LOGIN_HEADER = By.cssSelector("div#login > p.headline");
    private static final By LOGIN_FORM = By.id("login_form");
    private static final By EMAIL_INPUT = By.id("email");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.xpath("//input[@type='submit']");
    private static final By LOGIN_ERROR_MESSAGE = By.cssSelector("div.span12 > div.error-area");
    private static final String EXPECTED_LOGIN_ERROR_MESSAGE = "Invalid credentials!";
    private static final String EXPECTED_PAGE_TITLE = "PlaceLab";
    private static final String EXPECTED_FORGOT_PASSWORD_MESSAGE = "Forgot your password?";

    private final WebDriver driver;

    public LoginPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void validateLoginPageContent() {
        final String actualPageTitle = driver.getTitle();
        final boolean isHeaderDisplayed = driver.findElement(LOGIN_HEADER).isDisplayed();
        Assert.assertTrue(isHeaderDisplayed);

        Assert.assertEquals(actualPageTitle, EXPECTED_PAGE_TITLE);
        Assert.assertTrue(
                driver.findElement(LOGIN_FORM).isDisplayed(),
                "Validate login form is displayed."
        );
        Assert.assertTrue(
                driver.findElement(EMAIL_INPUT).isDisplayed(),
                "Validate email field is displayed."
        );
        Assert.assertTrue(
                driver.findElement(PASSWORD_INPUT).isDisplayed(),
                "Validate password field is displayed."
        );

        final boolean isForgotPasswordMessageDisplayed =
                driver.findElement(By.cssSelector("div#password-area > a.link-btn")).isDisplayed();
        Assert.assertTrue(isForgotPasswordMessageDisplayed);

        final String actualForgotPasswordMessage =
                driver.findElement(By.cssSelector("div#password-area > a.link-btn")).getText();
        Assert.assertEquals(actualForgotPasswordMessage, EXPECTED_FORGOT_PASSWORD_MESSAGE);
    }

    public void validateLoginErrorMessage() {
        final String actualInvalidCredentialsErrorMessage =
                driver.findElement(LOGIN_ERROR_MESSAGE).getText();
        Assert.assertEquals(actualInvalidCredentialsErrorMessage, EXPECTED_LOGIN_ERROR_MESSAGE);
        Assert.assertTrue(
                driver.findElement(LOGIN_FORM).isDisplayed(),
                "Validate login form is displayed."
        );
    }

    public void enterCredentials(final String email, final String password) {
        this.driver.findElement(EMAIL_INPUT).sendKeys(email);
        this.driver.findElement(PASSWORD_INPUT).sendKeys(password);
    }

    public void clickSubmitLoginButton() {
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void SubmitLoginButtonByEnterKey() {
        driver.findElement(PASSWORD_INPUT).sendKeys(Keys.ENTER);
    }

    public void clickForgotPasswordLink() {
        driver.findElement(By.cssSelector("div#password-area > a.link-btn")).click();
    }
}
