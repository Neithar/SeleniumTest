package com.gitlab.rmarzec.pageObjects;
import com.gitlab.rmarzec.model.YTTile;
import com.gitlab.rmarzec.util.BasePage;
import org.openqa.selenium.*;
import java.util.ArrayList;
import java.util.List;

public class YouTubePage extends BasePage {

    private static final String URL = "https://www.youtube.com/";

    private final By acceptCookiesButton = By.cssSelector(
            "div.eom-buttons.style-scope.ytd-consent-bump-v2-lightbox " +
                    "ytd-button-renderer:nth-of-type(2) button");

    private final By overlay = By.cssSelector("tp-yt-iron-overlay-backdrop.opened");
    private final By shortsTab = By.cssSelector("[title='Shorts']");
    private final By firstShortChannel = By.cssSelector("div#metapanel a[href*='/shorts']");
    private final By searchInput = By.name("search_query");
    private final By videoTiles = By.cssSelector("div#contents ytd-video-renderer");
    private final By title = By.id("video-title");
    private final By channel = By.cssSelector("#channel-name a");
    private final By durationBadge = By.cssSelector(
            "ytd-thumbnail-overlay-time-status-renderer div.yt-badge-shape__text");

    public YouTubePage(WebDriver driver) {
        super(driver);
    }

    public void goToHomePage() {
        driver.get(URL);
    }

    public void acceptCookiesIfPresent() {
        try {
            waitUntilClickable(acceptCookiesButton).click();
            waitUntilInvisibility(overlay);
        } catch (Exception ignored) {
        }
    }

    public void goToShorts() {
        waitUntilClickable(shortsTab).click();
    }

    public String getFirstShortChannelName() {
        return waitUntilVisible(firstShortChannel).getText();
    }

    public void search(String keyword) {
        WebElement input = waitUntilVisible(searchInput);
        input.clear();
        input.sendKeys(keyword + Keys.ENTER);
    }

    public List<YTTile> getFirstNVideos(int n) {

        waitUntilNumberOfElementsMoreThan(videoTiles, n - 1);

        List<WebElement> videos = findAll(videoTiles);
        List<YTTile> result = new ArrayList<>();

        for (WebElement video : videos) {

            if (result.size() == n) break;

            YTTile tile = new YTTile();

            tile.setTitle(video.findElement(title).getText());
            tile.setChannel(video.findElement(channel).getText());

            List<WebElement> durationElements = video.findElements(durationBadge);

            if (!durationElements.isEmpty()) {
                String length = durationElements.get(0)
                        .getAttribute("textContent")
                        .replace("\n", "")
                        .trim();
                tile.setLength(length);
            } else {
                tile.setLength("live");
            }

            result.add(tile);
        }

        return result;
    }
}
