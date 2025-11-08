package utils;

import base.TestBase;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

/**
 * Utility class containing common helper methods for test automation
 */
public class TestUtils extends TestBase {


    /**
     * Waits for element to be displayed on page
     * @param element WebElement to wait for
     * @param timeoutInSeconds maximum time to wait
     */
    public void waitForElementToBeDisplayed(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element to be visible
     * @param element WebElement to wait for
     * @param timeoutInSeconds maximum time to wait
     */
    public void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element to be clickable
     * @param element WebElement to wait for
     * @param timeoutInSeconds maximum time to wait
     */
    public void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Scrolls to specified element on page
     * @param element WebElement to scroll to
     */
    public void scrollToElement(WebElement element){
        waitForElementToBeVisible(element,10);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }

    /**
     * Implements a pause in test execution
     * @param seconds number of seconds to wait
     */
    public void waitForSomeTime(int seconds) {
        try {
            Thread.sleep(seconds * 1000L); // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refreshes current page
     */
    public void refreshPage(){
        driver.navigate().refresh();
    }

    /**
     * Captures screenshot for test failure
     * @param testName name of the test case name
     * @param errorDescription description of the error
     * @return path to saved screenshot
     */
    public static String captureScreenshot(String testName, String errorDescription) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            File screenshotsDir = new File(System.getProperty("user.dir") + "/test-output/screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String cleanTestName = testName.replaceAll("[^a-zA-Z0-9]", "_").substring(0, Math.min(testName.length(), 30));
            String cleanErrorDesc = errorDescription.replaceAll("[^a-zA-Z0-9]", "_").substring(0, Math.min(errorDescription.length(), 30));
            String fileName = String.format("%s_%s_%s.png", cleanTestName, cleanErrorDesc, timestamp);
            String screenshotPath = screenshotsDir.getPath() + File.separator + fileName;
            File destination = new File(screenshotPath);

            FileUtils.copyFile(source, destination);
            logger.info("Screenshot saved: " + fileName);

            return screenshotPath;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }

    /**
     * Handles test failure by capturing screenshot and logging error
     * @param e Throwable containing error details
     */
    public void handleTestFailure(Throwable e, ExtentTest test) {
        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String errorDesc = (e.getMessage() != null) ? e.getMessage().substring(0, Math.min(50, e.getMessage().length())) : "UnknownError";
        String screenshotPath = captureScreenshot(testName,errorDesc);
        try {
            if (screenshotPath != null && !screenshotPath.isEmpty()) {
                test.fail("Test Failed. Screenshot: ",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
            test.log(Status.FAIL, "Test Failed: " + e.getMessage());
            logger.error("Test failed", e);
        } catch (Exception ex) {
            logger.error("Failed to attach screenshot", ex);
        }
        Assert.fail("Test failed: " + e.getMessage());
    }

}
