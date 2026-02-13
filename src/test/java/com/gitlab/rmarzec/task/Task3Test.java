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

    private static final String EXPECTED_URL = "https://www.w3schools.com/tags/tag_select.asp";
    private static final String EXPECTED_HEADER = "The select element";
    private static final String SELECT_OPTION = "Opel";

    @BeforeTest
    public void setUp() {
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


        if (!driver.getCurrentUrl().equals(EXPECTED_URL)) {
            System.out.println("Unexpected URL: " + driver.getCurrentUrl());
            driver.get(EXPECTED_URL);
        }

        w3Page.acceptCookiesIfPresent();
        w3Page.clickTryItYourself();
        w3Page.switchToTryItWindow();

        String headerText = w3Page.getHeaderText();
        Assert.assertEquals(headerText, EXPECTED_HEADER);
        System.out.println("Header: " + headerText);

        w3Page.selectOptionByVisibleText(SELECT_OPTION);
        System.out.println("Selected: " + w3Page.getSelectedOptionText());
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
