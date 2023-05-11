package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class Homepage {
    final private static By USER_ROLE =  By.id("user-role");
    final private static By USER_DROPDOWN_MENU  = By.cssSelector("div#user-name > i.icon-angle-down");
    final private static By SIGN_OUT_FIELD = By.linkText("Sign out");
    final private static By CREATE_REPORT_FIELD = By.cssSelector("a#create-menu");
    final private static By TRAFFIC_DASHBOARD_FIELD = By.cssSelector("li#traffic-dashboard-nav-item");
    final private static By REPORTS_FIELD = By.cssSelector("li#queries-nav-item");
    final private static By CREATE_REPORT_BUTTON = By.cssSelector("a#create-menu > i.icon-angle-down");
    final private static By SINGLE_PLACE_SEARCH_BUTTON = By.id("singleplacesearch");
    final private static By REPORTS_TABLE = By.id("queries-table-container");
    final private static By DELETE_ICON = By.xpath("//a[@data-original-title='Delete']");
    final private static By DELETE_REPORT_DIALOGUE = By.id("confirm-action-delete");
    final private static By DELETE_REPORT_CONFIRM_BUTTON = By.xpath("//a[contains(text(), 'Confirm')]");
    private final static String EXPECTED_PAGE_TITLE = "PlaceLab - demo";

    final private WebDriver driver;

    public Homepage(final WebDriver driver) {
        this.driver = driver;
    }

    public void validateHomepageContent(final String expectedUserRole) {
        final String actualPageTitle = driver.getTitle();
        Assert.assertEquals(actualPageTitle, EXPECTED_PAGE_TITLE);

        final boolean isCreateReportButtonDisplayed = driver.findElement(CREATE_REPORT_FIELD).isDisplayed();
        Assert.assertTrue(isCreateReportButtonDisplayed, "Validate is create report button displayed.");

        final boolean isTrafficDashboardButtonDisplayed = driver.findElement(TRAFFIC_DASHBOARD_FIELD).isDisplayed();
        Assert.assertTrue(isTrafficDashboardButtonDisplayed, "Validate is traffic dashboard button displayed.");

        final boolean isReportsButtonDisplayed = driver.findElement(REPORTS_FIELD).isDisplayed();
        Assert.assertTrue(isReportsButtonDisplayed, "Validate is reports button displayed.");

        this.validateUserRole(expectedUserRole);
    }

    public void validateCreateReportDropdownMenu() {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(SINGLE_PLACE_SEARCH_BUTTON));

        final boolean isSinglePlaceSearchButtonDisplayed = driver.findElement(SINGLE_PLACE_SEARCH_BUTTON).isDisplayed();
        Assert.assertTrue(isSinglePlaceSearchButtonDisplayed,
                "Validate is single place search button displayed."
        );
    }

    public void validateUserRole(final String expectedUserRole) {
        final String actualRole = driver.findElement(USER_ROLE).getText();
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
        Assert.assertTrue(isSinglePlaceSearchButtonDisplayed,
                "Validate is single place search button displayed."
        );

        driver.findElement(SINGLE_PLACE_SEARCH_BUTTON).click();
    }

    public void deleteTestData(final String reportID) {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(REPORTS_TABLE));

        this.findReportCheckboxByReportID(reportID).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_ICON));
        final boolean isDeleteButtomEnabled = driver.findElement(DELETE_ICON).isEnabled();
        Assert.assertTrue(isDeleteButtomEnabled, "Validate is delete button enabled.");
        driver.findElement(DELETE_ICON).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirm-action-delete")));
        final boolean isDeleteDialogueDisplayed = driver.findElement(DELETE_REPORT_DIALOGUE).isDisplayed();
        Assert.assertTrue(isDeleteDialogueDisplayed, "Validate delete report dialogue is displayed.");

        driver.findElement(DELETE_REPORT_CONFIRM_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(REPORTS_FIELD));
    }

    private WebElement findReportCheckboxByReportID(final String reportID) {
        final String xpathByID = "//input[@value='" + reportID + "']";
        return driver.findElement(By.xpath("//div" + xpathByID)).findElement(By.xpath("./.."));
    }

    public void signOut() {
        this.clickOnUserDropdownMenu();
        Assert.assertTrue(
                driver.findElement(SIGN_OUT_FIELD).isDisplayed(), "Validate is Sign out button displayed"
        );

        driver.findElement(SIGN_OUT_FIELD).click();
    }
}
