package com.placelab.aldinrizvo.pages;

import org.openqa.selenium.WebDriver;

public class CreateSinglePlaceSearchReportLoadingPage {
    private final WebDriver driver;

    public CreateSinglePlaceSearchReportLoadingPage(final WebDriver driver) {
        this.driver = driver;
    }

    public String getReportRequestID() {
        final char[] url = driver.getCurrentUrl().toCharArray();
        final StringBuilder reportID = new StringBuilder();
        int counter = url.length - 1;
        while (url[counter] != '/') {
            reportID.append(url[counter]);
            counter--;
        }

        return reportID.reverse().toString();
    }
}
