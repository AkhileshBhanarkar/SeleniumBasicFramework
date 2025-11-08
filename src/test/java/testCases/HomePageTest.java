package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import utils.TestUtils;

public class HomePageTest extends TestBase {

    HomePage homePage;
    TestUtils testUtils;


    public HomePageTest() {
        super();//will call parent class constructor (TestBase class)
    }

    @BeforeMethod
    public void setup() {
        initializeDriver();
        homePage = new HomePage();
        testUtils = new TestUtils();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test(priority = 1)
    public void validateAmazonLogoTest() {
        try {
            test = extent.createTest("Validate Amazon Logo Test");
            logInfo("Starting validateAmazonLogoTest");

            boolean logo = homePage.validateAmazonLogo();

            if (logo) {
                logPass("Amazon logo is visible on the page");
            } else {
                logFail("Amazon logo visibility check failed: Logo not found on page");
                Assert.fail("Amazon logo is not displayed!");
            }

            logPass("Amazon logo validation completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 2)
    public void clickCartIconTest() {
        try {
            test = extent.createTest("Validate Cart Icon Click Test");
            logInfo("Starting clickCartIconTest");

            CartPage cartPage = homePage.clickCartIcon();
            logPass("Clicked the cart icon");

            if (cartPage != null) {
                logPass("Cart page object created successfully");
            } else {
                logFail("Cart page navigation failed: Page object is null");
                Assert.fail("Cart Page was not displayed.");
            }

            logPass("Cart icon click and navigation flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 3)
    public void getCartCountTest() {
        try {
            test = extent.createTest("Validate Cart Count Test");
            logInfo("Starting getCartCountTest");

            int count = homePage.getCartCount();
            logPass("Retrieved cart count: " + count);

            if (count >= 0) {
                logPass("Cart count value is valid: " + count);
            } else {
                logFail("Cart count validation failed: Negative count detected: " + count);
                Assert.fail("Cart count should not be negative!");
            }

            logPass("Cart count validation completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 4)
    public void validateTodaysDealsNavigationTest() {
        try {
            test = extent.createTest("Validate Today's Deals Navigation Test");
            logInfo("Starting validateTodaysDealsNavigationTest");

            homePage.clickTodaysDeals();
            logPass("Clicked on Today's Deals link");

            boolean isDealsPageDisplayed = homePage.isDealsPageDisplayed();

            if (isDealsPageDisplayed) {
                logPass("Today's Deals page is visible");
            } else {
                logFail("Today's Deals page visibility check failed: Page not displayed");
                Assert.fail("Deals page is not displayed!");
            }

            logPass("Today's Deals navigation flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

}
