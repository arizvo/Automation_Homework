package com.placelab.aldinrizvo.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverSetup {
    public static WebDriver getWebDriver(final String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                return getChromeDriver();
            case "firefox":
                return getFireFoxDriver();
            default:
                throw new IllegalArgumentException("Match case not found for browser: " + browserName);
        }
    }

    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private static WebDriver getFireFoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }
}
