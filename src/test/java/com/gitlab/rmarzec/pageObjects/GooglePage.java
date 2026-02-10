package com.gitlab.rmarzec.pageObjects;
import com.gitlab.rmarzec.util.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class GooglePage extends BasePage {

    private static final String URL = "https://www.google.com/";

    private final By acceptCookiesButton = By.id("L2AGLb");
    private final By searchInput = By.name("q");
    private final By luckyButton = By.name("btnI");

    public GooglePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
    }

    public void acceptCookiesIfPresent() {
        try {
            waitUntilClickable(acceptCookiesButton).click();
        } catch (Exception ignored) {}
    }

    public void search(String query) {
        WebElement input = waitUntilVisible(searchInput);
        input.clear();
        input.sendKeys(query);
    }

    public void clickLucky() {
        waitUntilClickable(luckyButton).click();
    }
}
