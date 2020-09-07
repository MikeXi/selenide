package com.epam.mail;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Collections;
import java.util.Random;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;

public class BasicTest {

    private static final String DRIVER_LOCATION = ".\\drivers\\chromedriver.exe";

    public static WebDriver driver;
    public static String emailSubject;

    @BeforeSuite
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", DRIVER_LOCATION);
        Configuration.browser="chrome";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        driver =  new ChromeDriver(options);
        setWebDriver(driver);
        Random r = new Random();
        emailSubject = "Automation test email Subject" + r.nextInt(1000);
    }

    @AfterSuite
    public void closeDriver(){
        getWebDriver().quit();
    }
}
