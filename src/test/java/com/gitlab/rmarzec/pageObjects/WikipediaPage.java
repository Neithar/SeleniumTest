package com.gitlab.rmarzec.pageObjects;
import com.gitlab.rmarzec.util.BasePage;
import org.openqa.selenium.*;

import java.util.List;

public class WikipediaPage extends BasePage {

    private static final String URL = "https://pl.wikipedia.org/wiki/Wiki";

    // Locators
    private final By languageButton = By.id("p-lang-btn");
    private final By languageLinks = By.cssSelector("div.row.uls-language-list.uls-lcd a.autonym");

    public WikipediaPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
    }

    public void clickLanguageButton() {
        waitUntilClickable(languageButton).click();
    }

    public List<WebElement> getAllLanguages() {
        return waitUntilVisibleAll(languageLinks);
    }

    public void printLanguages() {
        List<WebElement> langs = getAllLanguages();
        for (WebElement lang : langs) {
            String name = lang.getText();
            if ("English".equals(name)) {
                System.out.println(name + " - URL: " + lang.getAttribute("href"));
            } else {
                System.out.println(name);
            }
        }
    }
}
