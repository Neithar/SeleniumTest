package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;


public class Task2Test {
    @Test
    public void Task2Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // 1. Open Wikipedia
        webDriver.get("https://pl.wikipedia.org/wiki/Wiki");

        // 2. Click the language button
        WebElement languageButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("p-lang-btn"))
        );
        languageButton.click();

        // 3. Save all languages into a list
        List<WebElement> languages = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("div.row.uls-language-list.uls-lcd a.autonym")
                )
        );

        // 4. Iterate through the language list and print their names
        for (WebElement language : languages) {

            String languageName = language.getText();

            if ("English".equals(languageName)) {
                String url = language.getAttribute("href");
                System.out.println(languageName + " - URL: " + url);
            } else {
                System.out.println(languageName);
            }
        }

        webDriver.quit();
        
    }
}
