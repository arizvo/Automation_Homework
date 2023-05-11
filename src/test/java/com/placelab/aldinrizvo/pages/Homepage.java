package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Homepage {
    private static final By USER_ROLE =  By.id("user-role");
    private static final By USER_DROPDOWN_MENU  = By.cssSelector("div#user-name > i.icon-angle-down");
    private static final By SIGN_OUT_FIELD = By.linkText("Sign out");
    private static final By CREATE_REPORT_FIELD = By.cssSelector("a#create-menu");
    private static final By TRAFFIC_DASHBOARD_FIELD = By.cssSelector("li#traffic-dashboard-nav-item");
    private static final By REPORTS_FIELD = By.cssSelector("li#queries-nav-item");
    private static final By CREATE_REPORT_BUTTON = By.cssSelector("a#create-menu > i.icon-angle-down");
    private static final By SINGLE_PLACE_SEARCH_BUTTON = By.id("singleplacesearch");
    private static final By REPORTS_TABLE = By.id("queries-table-container");
    private static final By DELETE_ICON = By.xpath("//a[@data-original-title='Delete']");
    private static final By DELETE_REPORT_DIALOGUE = By.id("confirm-action-delete");
    private static final By DELETE_REPORT_CONFIRM_BUTTON = By.xpath("//a[contains(text(), 'Confirm')]");
    private final static String EXPECTED_PAGE_TITLE = "PlaceLab - demo";

    private final WebDriver driver;

    public Homepage(final WebDriver driver) {
        this.driver = driver;
    }

    public void validateHomepageContent(final String expectedUserRole) {
        final String actualPageTitle = this.driver.getTitle();
        Assert.assertEquals(actualPageTitle, EXPECTED_PAGE_TITLE);

        final boolean isCreateReportButtonDisplayed = this.driver.findElement(CREATE_REPORT_FIELD).isDisplayed();
        Assert.assertTrue(isCreateReportButtonDisplayed, "Validate is create report button displayed.");

        final boolean isTrafficDashboardButtonDisplayed = this.driver.findElement(TRAFFIC_DASHBOARD_FIELD).isDisplayed();
        Assert.assertTrue(isTrafficDashboardButtonDisplayed, "Validate is traffic dashboard button displayed.");

        final boolean isReportsButtonDisplayed = this.driver.findElement(REPORTS_FIELD).isDisplayed();
        Assert.assertTrue(isReportsButtonDisplayed, "Validate is reports button displayed.");

        this.validateUserRole(expectedUserRole);
    }

    public void validateCreateReportDropdownMenu() {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(SINGLE_PLACE_SEARCH_BUTTON));

        final boolean isSinglePlaceSearchButtonDisplayed = driver.findElement(SINGLE_PLACE_SEARCH_BUTTON).isDisplayed();
        Assert.assertTrue(
                isSinglePlaceSearchButtonDisplayed,
                "Validate is single place search button displayed."
        );
    }

    public void validateUserRole(final String expectedUserRole) {
        final String actualRole = this.driver.findElement(USER_ROLE).getText();
        Assert.assertEquals(actualRole, expectedUserRole, "Validate user role for logged in user.");
    }

    public void clickOnUserDropdownMenu() {
        Assert.assertTrue(
                driver.findElement(USER_DROPDOWN_MENU).isDisplayed(),
                "Validate dropdown menu  arrow is displayed. "
        );

        driver.findElement(USER_DROPDOWN_MENU).click();
    }

    public void clickOnCreateReportDropdownMenu() {
        final boolean isReportsButtonDisplayed = driver.findElement(CREATE_REPORT_BUTTON).isDisplayed();
        Assert.assertTrue(isReportsButtonDisplayed, "Validate is reports button displayed.");

        driver.findElement(CREATE_REPORT_BUTTON).click();
    }

    public void clickOnSinglePlaceSearchButton() {
        final boolean isSinglePlaceSearchButtonDisplayed = driver.findElement(SINGLE_PLACE_SEARCH_BUTTON).isDisplayed();
        Assert.assertTrue(
                isSinglePlaceSearchButtonDisplayed,
                "Validate is single place search button displayed."
        );

        driver.findElement(SINGLE_PLACE_SEARCH_BUTTON).click();
    }

    public void deleteTestData(final String reportID) {
        final WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(REPORTS_TABLE));

        this.findReportCheckboxByReportID(reportID).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_ICON));
        final boolean isDeleteButtonEnabled = this.driver.findElement(DELETE_ICON).isEnabled();
        Assert.assertTrue(isDeleteButtonEnabled, "Validate is delete button enabled.");
        this.driver.findElement(DELETE_ICON).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm-action-delete")));
        final boolean isDeleteDialogueDisplayed = this.driver.findElement(DELETE_REPORT_DIALOGUE).isDisplayed();
        Assert.assertTrue(isDeleteDialogueDisplayed, "Validate delete report dialogue is displayed.");

        this.driver.findElement(DELETE_REPORT_CONFIRM_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(REPORTS_FIELD));
    }

    private WebElement findReportCheckboxByReportID(final String reportID) {
        final String xpathByID = "//input[@value='" + reportID + "']";
        return this.driver.findElement(By.xpath("//div" + xpathByID)).findElement(By.xpath("./.."));
    }

    public void signOut() {
        this.clickOnUserDropdownMenu();
        Assert.assertTrue(
                driver.findElement(SIGN_OUT_FIELD).isDisplayed(),
                "Validate is Sign out button displayed"
        );

        this.driver.findElement(SIGN_OUT_FIELD).click();
    }
}
