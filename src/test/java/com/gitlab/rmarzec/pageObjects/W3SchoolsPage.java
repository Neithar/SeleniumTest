package com.gitlab.rmarzec.pageObjects;
import com.gitlab.rmarzec.util.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class W3SchoolsPage extends BasePage {

    private final By cookieIframe = By.id("fast-cmp-iframe");
    private final By acceptCookiesButton = By.cssSelector("span.fast-cmp-home-accept button");
    private final By tryItYourselfButton = By.cssSelector(".w3-btn[href*='tryit.asp?filename=tryhtml_select']");
    private final By iframeResult = By.id("iframeResult");
    private final By header = By.tagName("h1");
    private final By selectElement = By.tagName("select");

    public W3SchoolsPage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookiesIfPresent() {
        try {
            waitUntilVisible(cookieIframe);
            driver.switchTo().frame(driver.findElement(cookieIframe));
            waitUntilClickable(acceptCookiesButton).click();
            driver.switchTo().defaultContent();
        } catch (Exception ignored) {}
    }

    public void clickTryItYourself() {
        waitUntilClickable(tryItYourselfButton).click();
    }

    public void switchToTryItWindow() {
        waitUntilNumberOfWindowsToBe(2);
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(1));
    }

    public String getHeaderText() {
        driver.switchTo().frame(waitUntilVisible(iframeResult));
        return waitUntilVisible(header).getText();
    }

    public void selectOptionByVisibleText(String text) {
        WebElement selectEl = waitUntilVisible(selectElement);
        Select select = new Select(selectEl);
        select.selectByVisibleText(text);
    }

    public String getSelectedOptionText() {
        WebElement selectEl = waitUntilVisible(selectElement);
        Select select = new Select(selectEl);
        WebElement selected = select.getFirstSelectedOption();
        return selected.getText() + ", value: " + selected.getAttribute("value");
    }
}
