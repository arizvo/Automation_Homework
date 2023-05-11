package com.placelab.aldinrizvo.tests;

import com.github.javafaker.Faker;
import com.placelab.aldinrizvo.pages.CreateSinglePlaceSearchReportLoadingPage;
import com.placelab.aldinrizvo.pages.CreateSinglePlaceSearchReportPage;
import com.placelab.aldinrizvo.pages.Homepage;
import com.placelab.aldinrizvo.pages.LoginPage;
import com.placelab.aldinrizvo.utils.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Random;

public class CreateSinglePlaceSearchReportSmokeTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private Homepage homepage;
    private CreateSinglePlaceSearchReportPage createSinglePlaceSearchReportPage;
    private CreateSinglePlaceSearchReportLoadingPage createSinglePlaceSearchReportLoadingPage;

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void setup(final String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
        this.loginPage = new LoginPage(driver);
        this.homepage = new Homepage(driver);
        this.createSinglePlaceSearchReportPage = new CreateSinglePlaceSearchReportPage(driver);
        this.createSinglePlaceSearchReportLoadingPage = new CreateSinglePlaceSearchReportLoadingPage(driver);
    }

    @Parameters({"email", "password"})
    @Test(priority = 1, groups = {"CreateSinglePlaceSearchReport", "Positive"})
    public void smokeTestCreateSinglePlaceSearchReport(final String email, final String password) {
        loginPage.validateLoginPageContent();
        loginPage.enterCredentials(email, password);
        loginPage.clickSubmitLoginButton();

        final String expectedRole = "Group Admin";
        homepage.validateHomepageContent(expectedRole);
        homepage.clickOnCreateReportDropdownMenu();

        homepage.validateCreateReportDropdownMenu();
        homepage.clickOnSinglePlaceSearchButton();

        Faker faker = new Faker();
        Random random = new Random();
        final String reportName = faker.funnyName().name() + " " + random.nextInt(1,100);
        final String placeName = faker.country().name();
        final String phoneNumber =
                random.nextInt(100,500) + " " +
                random.nextInt(100,999) + " " +
                random.nextInt(100,9999) + "";
        final String address = faker.country().capital();

        createSinglePlaceSearchReportPage.validateCreateSinglePlaceSearchReportPageContent();
        createSinglePlaceSearchReportPage.fillAndSubmitSinglePlaceSearchReportForm(reportName, placeName, phoneNumber, address);

        final String reportID = createSinglePlaceSearchReportLoadingPage.getReportRequestID();

        homepage.validateHomepageContent(expectedRole);

        homepage.deleteTestData(reportID);

        homepage.validateHomepageContent(expectedRole);

        homepage.signOut();
    }

    @AfterMethod(alwaysRun = true, groups = {"Positive", "Negative"})
    public void teardown() {
        driver.close();
    }
}
