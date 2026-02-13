package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.pageObjects.YouTubePage;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.gitlab.rmarzec.model.YTTile;
import java.util.List;


public class Task4Test {

    private WebDriver driver;
    private YouTubePage ytPage;
    int videosToList = 12;

    @BeforeTest
    public void setUp() {
        driver = new DriverFactory().initDriver();
        ytPage = new YouTubePage(driver);
    }

    @Test
    public void listNonLiveVideosFromSearch() {

        ytPage.goToHomePage();
        ytPage.acceptCookiesIfPresent();

        ytPage.goToShorts();
        String shortChannelName = ytPage.getFirstShortChannelName();
        Assert.assertFalse(shortChannelName.isEmpty(), "Shorts channel is empty or has not been found");
        System.out.println("Shorts channel: " + shortChannelName);

        ytPage.goToHomePage(); // back to homepage
        ytPage.search("Live");

        List<YTTile> tiles = ytPage.getFirstNVideos(videosToList);
        Assert.assertFalse(tiles.isEmpty(), "The list of videos is empty or has not been found");

        for (YTTile tile : tiles) {
            if (!"live".equalsIgnoreCase(tile.getLength())) {
                System.out.println(tile.getTitle() + " | " + tile.getLength());
            }
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
