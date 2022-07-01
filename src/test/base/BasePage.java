package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void sleep(Long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void click(WebElement element) {
        moveIntoView(element);
        highlightElement(element);
        waitForElementClickability(element);
        element.click();
    }

    public void sendKeys(WebElement element, String inputText) {
        moveIntoView(element);
        highlightElement(element);
        waitForElementVisibility(element);
        element.sendKeys(inputText);
    }

    public String getText(WebElement element) {
        moveIntoView(element);
        highlightElement(element);
        waitForElementVisibility(element);
        return element.getText();
    }

    public boolean isDisplayed(WebElement element) {
        moveIntoView(element);
        highlightElement(element);
        waitForElementVisibility(element);
        return element.isDisplayed();
    }

    public boolean isEnabled(WebElement element) {
        moveIntoView(element);
        highlightElement(element);
        waitForElementVisibility(element);
        return element.isEnabled();
    }


    public void hoverOver(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void waitForElementClickability(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void moveIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 4; i++) {
            try {
                if (i % 2 == 0) {
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black;" +
                            "border: 3px solid red; background: yellow");
                } else {
                    sleep(500L);
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
