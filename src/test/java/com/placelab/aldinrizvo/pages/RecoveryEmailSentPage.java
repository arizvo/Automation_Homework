package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class RecoveryEmailSentPage {
    private static final By RECOVERY_EMAIL_SENT_HEADER = By.cssSelector("div#login > p.bold");
    private static final By EMAIL_SENT_CONFIRMATION = By.xpath("//*[contains(text(),'Email sent.')]");
    private static final By DIDNT_GET_EMAIL_LINK = By.cssSelector("div.small-headline > a.link-btn");
    private static final String EXPECTED_HEADER_TEXT = "We have sent you a link to change your password";
    private static final String EXPECTED_PAGE_TITLE = "PlaceLab";
    private static final String EXPECTED_EMAIL_SENT_CONFIRMATION_TEXT = "Email sent. Didn't get it?";

    private final WebDriver driver;

    public RecoveryEmailSentPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void validateRecoveryEmailSentPageContent() {
        final String actualPageTitle = driver.getTitle();
        Assert.assertEquals(actualPageTitle, EXPECTED_PAGE_TITLE);

        final boolean isHeaderDisplayed = driver.findElement(RECOVERY_EMAIL_SENT_HEADER).isDisplayed();
        Assert.assertTrue(isHeaderDisplayed);

        final String actualHeaderText = driver.findElement(RECOVERY_EMAIL_SENT_HEADER).getText();
        Assert.assertEquals(actualHeaderText, EXPECTED_HEADER_TEXT);

        final String actualEmailSentConfirmationMessage =
                driver.findElement(EMAIL_SENT_CONFIRMATION).getText();
        Assert.assertEquals(actualEmailSentConfirmationMessage, EXPECTED_EMAIL_SENT_CONFIRMATION_TEXT);

        final boolean isDidntGetEmailLinkDisplayed =
                driver.findElement(DIDNT_GET_EMAIL_LINK).isDisplayed();
        Assert.assertTrue(isDidntGetEmailLinkDisplayed, "Validate \"Didn't get it?\" link is displayed");
    }

    public void clickDidntGetEmailLink() {
        driver.findElement(DIDNT_GET_EMAIL_LINK).click();
    }
}
