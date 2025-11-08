package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import extentReport.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.TestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * Base class for all test classes containing common functionality and configurations
 */
public class TestBase {

    public static WebDriver driver;
    public static Properties properties;
    public static TestUtils utils;
    protected static final Logger logger = LogManager.getLogger(TestBase.class);
    protected static ExtentReports extent;
    protected ExtentTest test;

    /**
     * Constructor that loads configuration properties from file
     * Initializes the properties object and loads configuration settings
     */
    public TestBase() {
        try {

            logger.info("Reading properties from config file");
            properties = new Properties();
            // Load config from test/resources
            FileInputStream fis = new FileInputStream("src/main/java/config/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            logger.error("Failed to load properties file", e);
        }
    }

    /**
     * Initializes WebDriver based on browser configuration
     * Sets up browser instance with appropriate options including headless mode as well
     * Configures window size, navigation and wait times
     */
    public void initializeDriver() {
        logger.info("Initializing WebDriver for " + properties.getProperty("browser"));
        String browser = properties.getProperty("browser"); // Browser from config
        boolean headless = Boolean.parseBoolean(properties.getProperty("headless")); // Headless mode

        switch (browser.toLowerCase()) {
            case "chrome":
                logger.info("Initializing driver for Chrome browser...");
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                logger.info("Initializing driver for Firefox browser...");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                logger.info("Initializing driver for Edge browser...");
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalStateException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.get(properties.getProperty("url"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        utils = new TestUtils();
        List<WebElement> continueButtons = driver.findElements(By.xpath("//button[text()='Continue shopping']"));
        if (!continueButtons.isEmpty() && continueButtons.get(0).isDisplayed()) {
            continueButtons.get(0).click();
            utils.waitForElementToBeVisible(driver.findElement(By.id("nav-link-accountList")), 10);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(properties.getProperty("globalWait"))));
    }

    /**
     * Sets up ExtentReports instance before test suite execution
     * Called by TestNG before suite starts
     */
    @BeforeSuite
    public void setUpReport() {
        extent = ExtentManager.getInstance();
    }

    /**
     * Flushes and closes ExtentReports instance after test suite completion
     * Ganerate the Extemt repot html file
     * Called by TestNG after suite ends
     */
    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Logs informational message to both logger and extent report
     * @param message Information message to be logged
     */
    protected void logInfo(String message) {
        logger.info(message);
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    /**
     * Logs test pass message to both logger and extent report
     * @param message Success message to be logged
     */
    protected void logPass(String message) {
        logger.info("PASS: " + message);
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    /**
     * Logs test failure message to both logger and extent report
     * @param message Failure message to be logged
     */
    protected void logFail(String message) {
        logger.error("FAIL: " + message);
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }
}
