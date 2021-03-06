package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.Driver;

/**
 * Created by Serguei on 6/15/2017.
 */
public class AcornPrograms extends Page {

    @FindBy(id = "inputID")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(name = "login")
    private WebElement loginButton;

    private static final String url = "https://acorn.utoronto.ca/sws/welcome.do?welcome.dispatch#/programs";
    private Driver driver;

    public AcornPrograms(Driver driver) {
        PageFactory.initElements(driver, this);
    }

    public static String getUrl() {
        return url;
    }

    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
