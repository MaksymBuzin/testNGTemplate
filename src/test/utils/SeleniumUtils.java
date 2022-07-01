package utils;

import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class SeleniumUtils {
    public static String switchToWindowAndVerifyTitle(WebDriver driver) {
        String currentWindowID = driver.getWindowHandle();
        String title = "";

        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String each : allWindowIDs) {
            if (!each.equals(currentWindowID)) {
                driver.switchTo().window(each);
                title = driver.getTitle();
                driver.close();
            }
        }

        driver.switchTo().window(currentWindowID);
        return title;
    }

    /**
     * Method will switch to the next window
     * NOTE: Method works only with 2 windows at a time
     */
    public static void switchToNextWindow(WebDriver driver) {
        String currentWindowID = driver.getWindowHandle();
        Set<String> allWindowIDs = driver.getWindowHandles();

        for (String each : allWindowIDs) {
            if (!each.equals(currentWindowID))
                driver.switchTo().window(each);
        }
    }

    /**
     * get today's date in specific format
     */
    public static String getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

}
