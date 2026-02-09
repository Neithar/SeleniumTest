package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Task3Test {

    @Test
    public void Task3Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // 1. Open Google
        webDriver.get("https://www.google.com/");

        // 2. Accept cookies
        try {
            WebElement acceptCookiesButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("L2AGLb"))
            );
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("Cookies already accepted or button not found");
        }

        // 3. Enter query into the searchbar
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("q"))
        );
        searchInput.sendKeys("HTML select tag - W3Schools");

        // 4. Click "I'm Feeling Lucky"
        WebElement luckyButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("btnI"))
        );
        luckyButton.click();

        // 5. Verify URL
        String expectedUrl = "https://www.w3schools.com/tags/tag_select.asp";
        wait.until(ExpectedConditions.urlContains("w3schools.com/tags/tag_select.asp"));
        String currentUrl = webDriver.getCurrentUrl();

        if (!expectedUrl.equals(currentUrl)) {
            System.out.println("Redirect failed. Current URL: " + currentUrl);
            webDriver.get(expectedUrl);
        }

        // 6. Accept cookies on W3Schools
        try {
            WebElement cookieIframe = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("fast-cmp-iframe"))
            );
            webDriver.switchTo().frame(cookieIframe);

            WebElement accepW3CookiesButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("span.fast-cmp-home-accept button"))
            );
            accepW3CookiesButton.click();
        } catch (Exception e) {
            System.out.println("Cookies already accepted or button not found");
        }
        webDriver.switchTo().defaultContent();

        // 7. Click "Try it Yourself"
        WebElement tryItYourselfButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".w3-btn[href*='tryit.asp?filename=tryhtml_select']"))
        );
        tryItYourselfButton.click();

        // 7.1 Switch to the new window
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        List<String> windows = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(windows.get(1));

        // 8. Get the header inside iframe
        WebElement iframe = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("iframeResult"))
        );
        webDriver.switchTo().frame(iframe);

        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        // 9. Print the header text
        System.out.println("Header text: " + header.getText());

        // 10. Select "Opel" from the dropdown
        WebElement selectElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("select"))
        );
        Select select = new Select(selectElement);
        select.selectByVisibleText("Opel");

        // 11. Get selected option and print text + value
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println("Selected: " + selectedOption.getText() + ", value: " + selectedOption.getAttribute("value"));

        webDriver.quit();

    }
}
