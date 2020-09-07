package com.epam.mail;

//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage {
    final WebDriver driver;
    final int timeout;
//    final WebDriverWait wait;
//    final JavascriptExecutor jsExecutor;
    final Actions actionProvider;

    public BasePage(WebDriver driver){
        this.driver = driver;
        timeout = 10000;
//        wait = new WebDriverWait(driver,10);
//        jsExecutor = (JavascriptExecutor)driver;
        actionProvider = new Actions(driver);
    }

    public void highlightElementByJS(SelenideElement element){
        executeJavaScript("arguments[0].setAttribute(\"style\", arguments[1]);",element, "border: 2px solid red;");
    }
//
    public void clickElementByJS(SelenideElement element) {
        executeJavaScript("arguments[0].click();", element);
    }
//
//    public void dragAndDrop(WebElement sourceElement, WebElement targetElement) {
//        actionProvider.dragAndDrop(sourceElement, targetElement).build().perform();
//    }
//
//    public void contextClick(WebElement element) {
//        actionProvider.contextClick(element).build().perform();
//        sleepSeconds(2);
//    }
//
    public void sendKey(Keys key) {
        actionProvider.sendKeys(key).build().perform();
    }

    public void sleepSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
