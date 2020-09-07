package com.epam.mail;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage extends BasePage{
    private static final String URL = "https://gmail.com/";
    private By initialViewLocator = By.id("initialView");
    private By mailInputLocator = By.id("identifierId");
    private By emaiNextButtonLocator = By.cssSelector("div.VfPpkd-RLmnJb");
    private By passwordInputLocator = By.name("password");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public String loginGmail(String email, String password){
        MailBoxPage mailBoxPage = new MailBoxPage(driver);
        open(URL);
        $(mailInputLocator).setValue(email);
        clickElementByJS($(emaiNextButtonLocator));
        $(passwordInputLocator).waitUntil(exist, timeout * 1000).setValue(password);
        try {
            clickElementByJS($(emaiNextButtonLocator));
        }catch (StaleElementReferenceException e){
            clickElementByJS($(emaiNextButtonLocator));
        }
        return mailBoxPage.getAccountEmail();
    }

    public SelenideElement getInitialView(){
        return $(initialViewLocator).waitUntil(exist, timeout);
    }
}
