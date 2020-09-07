package com.epam.mail;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import java.util.List;


public class MailBoxPage extends BasePage{
    private static final String TO = "mike_xi@epam.com";
    private static final String BODY = "Automation test email Body";

    private By accoutInfoLocator = By.cssSelector("a[class='gb_D gb_Ua gb_i']");
    private By addEmailButtonLocator = By.cssSelector("div[gh='cm']");
    private By rowsLocator = By.cssSelector("tr[role='row']");
    private By emailRowLocator = By.cssSelector("span.bog");
    private By draftRowCountLocator = By.cssSelector("div.bsU");
    private By draftMenuLocator = By.cssSelector("div[data-tooltip='Drafts']");
    private By sentMenuLocator = By.cssSelector("div[data-tooltip='Sent']");
    private By starredMenuLocator = By.cssSelector("div[data-tooltip='Starred']");
    private By searchTextLocator = By.cssSelector("input[aria-label='Search mail']");
    private By logoutButtonLocator = By.cssSelector("a[class='gb_Mb gb_mg gb_ug gb_7e gb_7c']");

    public MailBoxPage(WebDriver driver){
        super(driver);
    }

    public String getAccountEmail(){
        SelenideElement account = $(accoutInfoLocator).waitUntil(exist, timeout);
        String accountInfos = account.getAttribute("aria-label");
        String[] accountInfo = accountInfos.split(":");
        return accountInfo[1].trim();
    }


    public boolean addDraftEmail(String emailSubject){
        int draftCountBefore = getDraftMailCount();
        MessageDialogPage messageDialogPage = new MessageDialogPage(driver);
        clickElementByJS($(addEmailButtonLocator));
        messageDialogPage.setMailContents(TO,emailSubject,BODY);
        messageDialogPage.closeMessageDialog();
        sleepSeconds(2);
        int draftCountAfter = getDraftMailCount();
        clickFirstEmailSubject(draftMenuLocator,emailSubject);
        return draftCountAfter == draftCountBefore + 1;
    }

    public SelenideElement getSentEmail(String emailSubject){
        return getEmailWithSubject(sentMenuLocator,emailSubject);
    }

    public SelenideElement searchEmail(String emailSubject){
        $(searchTextLocator).setValue(emailSubject).pressEnter();
        sleepSeconds(2);
        return getEmailWithSubject(sentMenuLocator,emailSubject);
    }

    public SelenideElement dragSentMailToStarred(String emailSubject){
        SelenideElement sentEmail = getEmailWithSubject(sentMenuLocator, emailSubject);
        highlightElementByJS(sentEmail);
        SelenideElement starredMenu = $(starredMenuLocator);
        highlightElementByJS(starredMenu);
        sentEmail.dragAndDropTo(starredMenu);
        return getEmailWithSubject(starredMenuLocator,emailSubject);
    }

    public SelenideElement deleteEmail(String emailSubject){
        SelenideElement email = getEmailWithSubject(starredMenuLocator, emailSubject);
        highlightElementByJS(email);
        email.contextClick();
        sleepSeconds(2);
        for(int i = 0; i < 6; i ++){
            sendKey(Keys.DOWN);
        }
        sendKey(Keys.ENTER);
        sleepSeconds(2);
        return getEmailWithSubject(starredMenuLocator, emailSubject);
    }

    public boolean logOut(){
        clickElementByJS($(accoutInfoLocator));
        clickElementByJS($(logoutButtonLocator));
        LoginPage loginPage = new LoginPage(driver);
        SelenideElement login = loginPage.getInitialView();
        return login.isDisplayed();
    }

    public void tabClick(By menuLocator){
        String url = driver.getCurrentUrl();
        boolean isNotSearch = !(url.contains("search"));
        if(isNotSearch) {
            String menuName = url.split("#")[1].toUpperCase();
            boolean inocrrectMenu = !menuLocator.toString().toUpperCase().contains(menuName);
            if (inocrrectMenu) {
                SelenideElement menu = $(menuLocator);
                highlightElementByJS(menu);
                clickElementByJS(menu);
                while (inocrrectMenu) {
                    sleepSeconds(1);
                    url = driver.getCurrentUrl();
                    menuName = url.split("#")[1].toUpperCase();
                    inocrrectMenu = !menuLocator.toString().toUpperCase().contains(menuName);
                }
            }
        }
    }

    public int getDraftMailCount(){
        return Integer.parseInt($(draftMenuLocator).$(draftRowCountLocator).getText());
    }

    public List<SelenideElement> getRows(By menuLocator){
        tabClick(menuLocator);
        List<SelenideElement> rows = $$(rowsLocator);
        return rows;
    }

    public int getRowsCount(By menuLocator){
        List<SelenideElement> rows = getRows(menuLocator);
        return rows.size();
    }

    public SelenideElement getEmailWithSubject(By menuLocator, String emailSubject){
        SelenideElement firstEmail = null;
        List<SelenideElement> rows = getRows(menuLocator);
        int rowCount = getRowsCount(menuLocator);
        for(int i = 0; i < rowCount; i++){
            SelenideElement row = rows.get(i).$(emailRowLocator);
            String rowSubject = row.getText();
            if(rowSubject.equals(emailSubject)){
                firstEmail = row;
                highlightElementByJS(firstEmail);
                break;
            }
        }
        return firstEmail;
    }

    public void clickFirstEmailSubject(By menuLocator, String emailSubject){
        SelenideElement subjectCell = getEmailWithSubject(menuLocator,emailSubject);
        clickElementByJS(subjectCell);
    }

}
