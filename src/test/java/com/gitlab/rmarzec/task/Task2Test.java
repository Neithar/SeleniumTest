package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.pageObjects.WikipediaPage;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class Task2Test {

    private WebDriver driver;
    private WikipediaPage wikiPage;

    @BeforeTest
    public void beforeTest() {
        driver = new DriverFactory().initDriver();
        wikiPage = new WikipediaPage(driver);
    }

    @Test
    public void listLanguages() {
        wikiPage.open();
        wikiPage.clickLanguageButton();
        wikiPage.printLanguages();
    }

    @AfterTest
    public void afterTest() {
        if (driver != null) driver.quit();
    }
}
