package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.pageObjects.WikipediaPage;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;


public class Task2Test {

    private WebDriver driver;
    private WikipediaPage wikiPage;

    @BeforeTest
    public void setUp() {
        driver = new DriverFactory().initDriver();
        wikiPage = new WikipediaPage(driver);
    }

    @Test
    public void listWikipediaLanguages() {
        wikiPage.open();
        wikiPage.clickLanguageButton();

        List<WebElement> languages = wikiPage.getLanguageElements();
        Assert.assertFalse(languages.isEmpty(), "WebElement list is empty");

        for (WebElement lang : languages) {
            String name = lang.getText();
            if ("English".equals(name)) {
                System.out.println(name + " - URL: " + lang.getAttribute("href"));
            } else {
                System.out.println(name);
            }
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
