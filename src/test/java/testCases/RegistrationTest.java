package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegistrationPage;

import java.util.HashMap;

public class RegistrationTest extends TestBase {
    HomePage homePage;
    LoginPage loginPage;
    RegistrationPage registrationPage;


    public RegistrationTest(){
        super();
    }

    @BeforeMethod
    public void setup(){
        initializeDriver();
        homePage = new HomePage();
        loginPage = new LoginPage();
        registrationPage = new RegistrationPage();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test(priority = 1)
    public void verifyCreateAccountPageTest() {
        try {
            test = extent.createTest("Verify Create Account Page Test");
            logInfo("Starting verifyCreateAccountPageTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.loginWithNewUser(properties.getProperty("new_user_mobile_number"));
            logPass("Entered new user mobile number: " +
                    properties.getProperty("new_user_mobile_number"));

            loginPage.clickCreateAccountButton();
            logPass("Clicked create account button");

            String headerText = registrationPage.validateRegistrationPageHeaderText();
            String expectedHeader = "Create Account";

            if (headerText.equals(expectedHeader)) {
                logPass("Header text verified: [" + headerText + "]");
            } else {
                logFail("Registration page header verification failed. Expected: [" +
                        expectedHeader + "] but found: [" + headerText + "]");
                Assert.fail("Registration page header text mismatch!");
            }

            logPass("Create account page verification completed successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 2)
    public void completeRegistrationTest() {
        try {
            test = extent.createTest("Complete Registration Test");
            logInfo("Starting completeRegistrationTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            String newUserMobile = properties.getProperty("new_user_mobile_number");
            loginPage.loginWithNewUser(newUserMobile);
            logPass("Entered new user mobile number: " + newUserMobile);

            loginPage.clickCreateAccountButton();
            logPass("Clicked create account button");

            String customerName = properties.getProperty("customer_name");
            registrationPage.enterCustomerName(customerName);
            logPass("Entered customer name: " + customerName);

            registrationPage.enterPassword(properties.getProperty("password"));
            logPass("Entered password successfully");

            if (customerName != null && !customerName.isEmpty()) {
                logPass("Registration form filled completely");
            } else {
                logFail("Failed to fill registration form: Missing required fields");
                Assert.fail("Registration form not filled properly!");
            }

            logPass("Complete registration flow finished successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

}

