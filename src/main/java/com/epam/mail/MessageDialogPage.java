package com.epam.mail;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class MessageDialogPage extends BasePage{

    private By draftMenuLocator = By.cssSelector("div[data-tooltip='Drafts']");
    private By messageDialogLocator = By.cssSelector("div[role='dialog']");
    private By toTextAreaLocator = By.cssSelector("textarea[aria-label='To']");
    private By subjectTextLocator = By.cssSelector("input[name='subjectbox']");
    private By bodyTextLocator = By.cssSelector("div[aria-label='Message Body']");
    private By closeIconLocator = By.cssSelector("img[aria-label='Save & close']");
    private By toEmailLocator = By.cssSelector("input[name='to']");
    private By messageDialogTitleLocator = By.cssSelector("div.aYF");
    private By sendButtonLocator = By.cssSelector("div[data-tooltip^='Send']");

    MailBoxPage mailBoxPage;


    public MessageDialogPage(WebDriver driver){
        super(driver);
        mailBoxPage = new MailBoxPage(driver);
    }

    public void setMailContents(String to, String subject, String body){
        SelenideElement messageDialog = $(messageDialogLocator).waitUntil(exist, timeout);
        messageDialog.$(toTextAreaLocator).setValue(to);
        messageDialog.$(subjectTextLocator).setValue(subject);
        messageDialog.$(bodyTextLocator).setValue(body);
    }

    public String getMailContent(String emailSubject, String field){
        SelenideElement messageDialog = $(messageDialogLocator).waitUntil(exist, timeout);
        switch (field){
            case "TO":
                return messageDialog.$(toEmailLocator).getAttribute("value");
            case "SUBJECT":
                return messageDialog.$(messageDialogTitleLocator).getText();
            default:
                return messageDialog.$(bodyTextLocator).getText();
        }
    }

    public Boolean sendEmail(){
        int beforeSendDraftCount = mailBoxPage.getDraftMailCount();
        $(messageDialogLocator).$(sendButtonLocator).click();
        mailBoxPage.sleepSeconds(2);
        int afterSendDraftCount = mailBoxPage.getDraftMailCount();
        if(afterSendDraftCount == beforeSendDraftCount - 1){
            return true;
        }else{
            return false;
        }
    }

    public void closeMessageDialog(){
        $(closeIconLocator).click();
    }
}
