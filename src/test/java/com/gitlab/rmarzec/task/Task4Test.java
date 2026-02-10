package com.gitlab.rmarzec.task;
import com.gitlab.rmarzec.pageObjects.YouTubePage;
import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import com.gitlab.rmarzec.model.YTTile;
import java.util.List;


public class Task4Test {

    WebDriver driver = new DriverFactory().initDriver();
    YouTubePage ytPage = new YouTubePage(driver);

    @Test
    public void YouTubeTest() {

        ytPage.open();
        ytPage.acceptCookiesIfPresent();

        ytPage.goToShorts();
        System.out.println("Shorts channel: " + ytPage.getFirstShortChannelName());

        ytPage.open(); // back to homepage
        ytPage.search("Live");

        List<YTTile> tiles = ytPage.getFirstNVideos(12);

        for (YTTile tile : tiles) {
            if (!"live".equalsIgnoreCase(tile.getLength())) {
                System.out.println(tile.getTitle() + " | " + tile.getLength());
            }
        }
    }

    @AfterTest
    public void CloseBrowser() {
        driver.quit();
    }
}
