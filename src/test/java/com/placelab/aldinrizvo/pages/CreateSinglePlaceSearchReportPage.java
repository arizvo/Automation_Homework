package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CreateSinglePlaceSearchReportPage {
    private static final By REPORT_FORM = By.id("single_poi_dialog");
    private static final By HEADLINE = By.cssSelector("div.report-header");
    private static final By REPORT_NAME = By.id("name");
    private static final By PLACE_NAME = By.id("single_text");
    private static final By LOCATION_FIELD_TEXT = By.cssSelector("div.form-group > label.required-after");
    private static final By CREATE_REPORT_BUTTON = By.xpath("//button[contains(text(), 'Create Report')]");
    private static final By PHONE_INPUT = By.id("single_phone");
    private static final By LOCATION_INPUT = By.id("location_name");
    private static final By CATEGORY_DROPDOWN_MENU_BUTTON = By.cssSelector("div.btn-group > button.multiselect > b.caret");
    private static final By LOCATION_CONFIRMATION_DIALOGUE = By.cssSelector("div.ui-dialog");
    private static final By LOCATION_CONFIRMATION_YES_BUTTON = By.xpath("//button[contains(text(), 'Yes')]");
    private static final By CATEGORIES = By.cssSelector("ul > div.options-wrapper");
    private static final By LOCATION_SUGGESTION = By.xpath("//ul[@class='typeahead dropdown-menu']//li[@class='active']");
    private static final String EXPECTED_REPORT_NAME_FIELD_TEXT = "Enter report name...";
    private static final String EXPECTED_PLACE_NAME_FIELD_TEXT = "Place Name";
    private static final String EXPECTED_LOCATION_FIELD_TEXT = "On the following location";

    private final WebDriver driver;

    public CreateSinglePlaceSearchReportPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void validateCreateSinglePlaceSearchReportPageContent() {
        final boolean isHeadlineTextDisplayed = driver.findElement(HEADLINE).isDisplayed();
        Assert.assertTrue(isHeadlineTextDisplayed);

        final boolean isReportFormDisplayed = driver.findElement(REPORT_FORM).isDisplayed();
        Assert.assertTrue(isReportFormDisplayed, "Validate Single place search report form is displayed.");

        Assert.assertFalse(this.isCreateReportButtonEnabled(), "Validate create report button is disabled.");

        final String actualReportNameFieldText = driver.findElement(REPORT_NAME).getAttribute("placeholder");
        Assert.assertEquals(actualReportNameFieldText, EXPECTED_REPORT_NAME_FIELD_TEXT);

        final String actualPlaceNameFieldText = driver.findElement(PLACE_NAME).getAttribute("placeholder");
        Assert.assertEquals(actualPlaceNameFieldText, EXPECTED_PLACE_NAME_FIELD_TEXT);

        final String actualLocationFieldText = driver.findElement(LOCATION_FIELD_TEXT).getText();
        Assert.assertEquals(actualLocationFieldText, EXPECTED_LOCATION_FIELD_TEXT);
    }

    public void fillAndSubmitSinglePlaceSearchReportForm(final String reportName,
                                                         final String placeName,
                                                         final String phoneNumber,
                                                         final String address) {
        final WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));

        Assert.assertFalse(this.isCreateReportButtonEnabled(), "Validate create report button is disabled1.");

        this.driver.findElement(REPORT_NAME).sendKeys(reportName);
        Assert.assertFalse(this.isCreateReportButtonEnabled(), "Validate create report button is disabled2.");

        this.driver.findElement(PLACE_NAME).sendKeys(placeName);
        Assert.assertFalse(this.isCreateReportButtonEnabled(), "Validate create report button is disabled3.");

        this.driver.findElement(PHONE_INPUT).sendKeys(phoneNumber);
        Assert.assertFalse(this.isCreateReportButtonEnabled(), "Validate create report button is disabled4.");

        this.driver.findElement(LOCATION_INPUT).sendKeys(address);
        this.driver.findElement(LOCATION_INPUT).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATION_SUGGESTION));
        this.driver.findElement(LOCATION_SUGGESTION).click();

        final boolean isLocationConfirmationDialogueDisplayed =
                this.driver.findElement(LOCATION_CONFIRMATION_DIALOGUE).isDisplayed();
        Assert.assertTrue(isLocationConfirmationDialogueDisplayed);

        wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATION_CONFIRMATION_YES_BUTTON));
        this.driver.findElement(LOCATION_CONFIRMATION_YES_BUTTON).click();
        Assert.assertTrue(this.isCreateReportButtonEnabled(), "Validate create report button is enabled.");

        // Select category
        this.driver.findElement(CATEGORY_DROPDOWN_MENU_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.getRandomCategoryForSinglePlaceSearch()));
        this.driver.findElement(this.getRandomCategoryForSinglePlaceSearch()).click();
        Assert.assertTrue(this.isCreateReportButtonEnabled(), "Validate create report button is enabled2.");

        this.driver.findElement(CREATE_REPORT_BUTTON).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private By getRandomCategoryForSinglePlaceSearch() {
        final List<String> listOfCategories = driver.findElement(CATEGORIES)
                .getText()
                .lines()
                .toList();
        final Random randomNumber = new Random();

        return By.xpath(
                "//label[contains(text(), '" +
                listOfCategories.get(randomNumber.nextInt(0, listOfCategories.size() - 1)) +
                "')]"
        );
    }

    private boolean isCreateReportButtonEnabled() {
        return driver.findElement(CREATE_REPORT_BUTTON).isEnabled();
    }
}
