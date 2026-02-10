package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import com.gitlab.rmarzec.pageObjects.GooglePage;
import com.gitlab.rmarzec.pageObjects.W3SchoolsPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class Task3Test {

    private WebDriver driver;
    private GooglePage googlePage;
    private W3SchoolsPage w3Page;

    @BeforeTest
    public void beforeTest() {
        driver = new DriverFactory().initDriver();
        googlePage = new GooglePage(driver);
        w3Page = new W3SchoolsPage(driver);
    }

    @Test
    public void testW3Schools() {

        googlePage.open();
        googlePage.acceptCookiesIfPresent();
        googlePage.search("HTML select tag - W3Schools");
        googlePage.clickLucky();

        String expectedUrl = "https://www.w3schools.com/tags/tag_select.asp";
        if (!driver.getCurrentUrl().contains("w3schools.com/tags/tag_select.asp")) {
            driver.get(expectedUrl);
        }

        w3Page.acceptCookiesIfPresent();
        w3Page.clickTryItYourself();
        w3Page.switchToTryItWindow();

        String headerText = w3Page.getHeaderText();
        System.out.println("Header: " + headerText);
        Assert.assertFalse(headerText.isEmpty(), "Header should not be empty");

        w3Page.selectOptionByVisibleText("Opel");
        System.out.println("Selected: " + w3Page.getSelectedOptionText());
    }

    @AfterTest
    public void afterTest() {
        if (driver != null) driver.quit();
    }
}
