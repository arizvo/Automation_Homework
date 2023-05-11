package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ForgotPasswordPage {
    private static final By FORGOT_PASSWORD_HEADER = By.cssSelector("div#login > p.headline");
    private static final By LOGIN_FORM = By.id("login_form");
    private static final By EMAIL_INPUT = By.id("email");
    private static final By CONTINUE_BUTTON = By.xpath("//input[@type='submit']");
    private static final String EXPECTED_HEADER_TEXT = "Change your password\nLet's find your account";
    private static final String EXPECTED_PAGE_TITLE = "PlaceLab";

    private final WebDriver driver;

    public ForgotPasswordPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void validateForgotPasswordPageContent() {
        final String actualPageTitle = driver.getTitle();
        final boolean isHeaderDisplayed = driver.findElement(FORGOT_PASSWORD_HEADER).isDisplayed();
        Assert.assertTrue(isHeaderDisplayed);

        final String actualHeaderText =
                driver.findElement(By.cssSelector("div#login > p.headline")).getText();
        Assert.assertEquals(actualHeaderText, EXPECTED_HEADER_TEXT);

        Assert.assertEquals(actualPageTitle, EXPECTED_PAGE_TITLE);
        Assert.assertTrue(
                driver.findElement(LOGIN_FORM).isDisplayed(),
                "Validate login form is displayed."
        );
        Assert.assertTrue(
                driver.findElement(EMAIL_INPUT).isDisplayed(),
                "Validate email field is displayed."
        );

        final boolean isContinueButtonDisplayed = driver.findElement(CONTINUE_BUTTON).isDisplayed();
        Assert.assertTrue(isContinueButtonDisplayed);
    }

    public void enterEmailCredential(final String email) {
        this.driver.findElement(EMAIL_INPUT).sendKeys(email);
    }

    public void clickContinueButton() {
        this.driver.findElement(CONTINUE_BUTTON).click();
    }

    public void continueByEnterKey() {
        this.driver.findElement(EMAIL_INPUT).sendKeys(Keys.ENTER);
    }
}
