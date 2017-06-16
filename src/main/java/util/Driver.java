package util;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Page;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by Serguei on 6/15/2017.
 */
public class Driver extends ChromeDriver {

    private FluentWait<WebDriver> wait;

    static {
        ChromeDriverManager.getInstance().setup();
    }

    public Driver() {
        // exit on finish
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Driver.super.quit();
            }
        });

        wait = new WebDriverWait(this, 10, 10);
    }

    public void visit(String url) {
        navigate().to(url);
    }

    public boolean isElementPresent(By by) {
        return getElementIfPresent(by) != null;
    }

    /**
     * Get element if it's immediately present, else return null
     */
    public WebElement getElementIfPresent(By by) {
        manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

        WebElement result;

        try {
            result = new WebDriverWait(this, 0)
                    .pollingEvery(10, TimeUnit.MILLISECONDS)
                    .until(presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            result = null;
        }

        manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return result;
    }

    public <T> T waitFor(ExpectedCondition<T> e) {
        return wait.until(e);
    }

    public <T> T waitFor(Supplier<T> s) {
        return wait.until(driver -> s.get());
    }

    public void click(WebElement webElement) {
        wait.until(successfulClick(webElement));
    }

    public void click(By by) {
        wait.until(successfulClick(by));
    }

    private ExpectedCondition<WebElement> successfulClick(WebElement webElement) {
        return driver -> {
            if (ExpectedConditions.elementToBeClickable(webElement).apply(driver) == null) {
                return null;
            }

            try {
                webElement.click();
                return webElement;
            } catch (WebDriverException e) {
                return null;
            }
        };
    }

    private ExpectedCondition<WebElement> successfulClick(By by) {
        return driver -> {
            WebElement webElement = ExpectedConditions.elementToBeClickable(by).apply(driver);
            if (webElement == null) {
                return null;
            }

            try {
                webElement.click();
                return webElement;
            } catch (WebDriverException e) {
                return null;
            }
        };
    }

}
