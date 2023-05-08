package com.placelab.aldinrizvo.tests;

import com.placelab.aldinrizvo.utilis.WebDriverSetup;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.plaf.InternalFrameUI;

public class SmokeTest {
    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = WebDriverSetup.getWebDriver(System.getProperty("browser"));
        driver.get("https://demo.placelab.com/");
    }

    @Test
    public void openPage() {
        try {
            System.out.println("Opened driver: " + System.getProperty("browser"));
            System.out.println("Webpage title: " + driver.getTitle());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void teardown() {
        driver.close();
    }
}
