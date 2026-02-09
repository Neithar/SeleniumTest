package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.gitlab.rmarzec.model.YTTile;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class Task4Test {

    @Test
    public void Task4Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        
        //Lista kafelkow
        List<YTTile> ytTileList = new ArrayList<>();

        // 1. Open YouTube
        webDriver.get("https://www.youtube.com/");

        // 2. Accept cookies
        try {
            WebElement acceptCookies = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("div.eom-buttons.style-scope.ytd-consent-bump-v2-lightbox ytd-button-renderer:nth-of-type(2) button")
                    )
            );
            acceptCookies.click();
        } catch (Exception e) {
            System.out.println("Cookies already accepted or button not found");
        }

        // Wait for the overlay that appears after accepting cookies to disappear
        By overlay = By.cssSelector("tp-yt-iron-overlay-backdrop.opened");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

        // 3. Go to Shorts
        WebElement shortsTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[title='Shorts']")
                )
        );
        shortsTab.click();

        // 4. Print channel name from first short
        WebElement channelNameShort = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div#metapanel a[href*='/shorts']")
                )
        );
        System.out.println("Shorts channel: " + channelNameShort.getText());

        // 5. Back to homepage
        webDriver.get("https://www.youtube.com/");

        // 6. Search for "Live"
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("search_query"))
        );
        searchInput.sendKeys("Live" + Keys.ENTER);

        // 7. Collect first 12 non-Shorts videos into a list
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("div#contents ytd-video-renderer"), 11
        ));

        List<WebElement> videoElements = webDriver.findElements(
                By.cssSelector("div#contents ytd-video-renderer"));

        // Get title, channel name and length from each element on the Web Element list and add them to the TileList
        for (WebElement video : videoElements) {

            if (ytTileList.size() == 12) break;

            YTTile tile = new YTTile();

            String title = video.findElement(By.id("video-title")).getText();
            String channel = video.findElement(By.cssSelector("#channel-name a")).getText();

            tile.setTitle(title);
            tile.setChannel(channel);

            // Set the length to the displayed time or "live", depending on whether the tim badge is present at all
            List<WebElement> durationElements = video.findElements(
                    By.cssSelector("ytd-thumbnail-overlay-time-status-renderer div.yt-badge-shape__text")
            );

            if (!durationElements.isEmpty()) {
                String length = durationElements.get(0)
                        .getAttribute("textContent")
                        .replace("\n", "")
                        .trim();

                tile.setLength(length);
            } else {
                tile.setLength("live");
            }

            ytTileList.add(tile);
        }

        // 8. Log videos that are not live
        for (YTTile tile : ytTileList) {
            if (!"live".equalsIgnoreCase(tile.getLength())) {
                System.out.println(tile.getTitle() + " | " + tile.getLength());
            }
        }

        webDriver.quit();
        
    }
}
