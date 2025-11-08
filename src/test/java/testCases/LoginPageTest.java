package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginPageTest extends TestBase {
    HomePage homePage;
    LoginPage loginPage;


    public LoginPageTest(){
        super();//will call parent class constructor
    }

    @BeforeMethod
    public void setup(){
        initializeDriver();
        homePage = new HomePage();
        loginPage = new LoginPage();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test(priority = 1) // failing this test case intentionally
    public void loginPageHeaderTest() {
        try {
            test = extent.createTest("Verify Login Page Header Test");
            logInfo("Starting loginPageHeaderTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            String headerText = loginPage.validateSignInOrCreateAccountHeaderText();
            String expectedText = "Sign in or create account - WRONG TEXT";

            if (headerText.equals(expectedText)) {
                logPass("Login page header text validated successfully");
            } else {
                logFail("Login page header text is mismatched. Expected: ["
                        + expectedText + "] but found: [" + headerText + "]");
                Assert.fail("Header text mismatch!");
            }

            logPass("Validated login page header message successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 2)
    public void validateValidEmailIdTest() {
        try {
            test = extent.createTest("Verify Valid Email ID is allowed for Login");
            logInfo("Starting validateValidEmailIdTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.login(properties.getProperty("valid_email_id"));
            logPass("Entered valid email ID");

            String headerTextForSignIn = loginPage.validateSignInPageHeaderText();
            String expectedHeaderText = "Sign in";

            if (headerTextForSignIn.equals(expectedHeaderText)) {
                logPass("Sign in page header text validated successfully");
            } else {
                logFail("Sign in page header validation failed. Expected: [" + expectedHeaderText +
                        "] but found: [" + headerTextForSignIn + "]");
                Assert.fail("Sign in page header text mismatch!");
            }

            logPass("Validated valid email ID login flow successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 3)
    public void validateValidMobileNumberTest() {
        try {
            test = extent.createTest("Verify Valid Mobile Number is allowed for Login");
            logInfo("Starting validateValidMobileNumberTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.login(properties.getProperty("valid_mobile_number"));
            logPass("Entered valid mobile number");

            String headerTextForSignIn = loginPage.validateSignInPageHeaderText();
            String expectedHeaderText = "Sign in";

            if (headerTextForSignIn.equals(expectedHeaderText)) {
                logPass("Sign in page header text validated successfully");
            } else {
                logFail("Sign in page header validation failed. Expected: [" + expectedHeaderText +
                        "] but found: [" + headerTextForSignIn + "]");
                Assert.fail("Sign in page header text mismatch!");
            }

            logPass("Validated valid mobile number login flow successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 4)
    public void validateInvalidEmailIdTest() {
        try {
            test = extent.createTest("Verify invalid Email ID is not allowed for Login");
            logInfo("Starting loginWithInvalidEmailTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.login(properties.getProperty("invalid_email_id"));
            logPass("Entered invalid email ID");

            String errorMessage = loginPage.getInvalidEmailAlertText();
            String expectedErrorMessage = "Invalid email address.";

            if (errorMessage.equals(expectedErrorMessage)) {
                logPass("Invalid email error message matched expected text");
            } else {
                logFail("Invalid email error message validation failed. Expected: [" +
                        expectedErrorMessage + "] but found: [" + errorMessage + "]");
                Assert.fail("Invalid email error message mismatch!");
            }

            logPass("Invalid email validation flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 5)
    public void validateInvalidMobileNumberTest() {
        try {
            test = extent.createTest("Verify invalid Mobile Number is not allowed for Login");
            logInfo("Starting loginWithInvalidMobileNumberTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.login(properties.getProperty("invalid_mobile_number"));
            logPass("Entered invalid mobile number");

            String errorMessage = loginPage.getInvalidPhoneNumberAlertText();
            String expectedErrorMessage = "Invalid mobile number";

            if (errorMessage.equals(expectedErrorMessage)) {
                logPass("Invalid mobile number error message matched expected text");
            } else {
                logFail("Invalid mobile number error validation failed. Expected: [" +
                        expectedErrorMessage + "] but found: [" + errorMessage + "]");
                Assert.fail("Invalid mobile number error message mismatch!");
            }

            logPass("Invalid mobile number validation flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 6)
    public void loginWithNewUserTest() {
        try {
            test = extent.createTest("Login with New User Test");
            logInfo("Starting loginWithNewUserTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.loginWithNewUser(properties.getProperty("new_user_mobile_number"));
            logPass("Entered new user mobile number");

            boolean isButtonDisplayed = loginPage.isCreateAccountButtonDisplayed();

            if (isButtonDisplayed) {
                logPass("Create Account button is visible on the page");
            } else {
                logFail("Create Account button visibility check failed: Expected button to be displayed but it was not found");
                Assert.fail("Create Account button not displayed for new user login!");
            }

            logPass("New user login flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 7) // failing this test case intentionally
    public void validateNewUserMessageTest() {
        try {
            test = extent.createTest("Validate New User Message Test");
            logInfo("Starting validateNewUserMessageTest");

            homePage.clickLoginInButton();
            logPass("Navigated to login page");

            loginPage.loginWithNewUser(properties.getProperty("new_user_mobile_number"));
            logPass("Entered new user mobile number");

            String newUserMessage = loginPage.getNewUserMessage();
            String expectedMessage = "Looks like you are new to Amazon - WRONG TEXT"; // intentionally wrong

            if (newUserMessage.equals(expectedMessage)) {
                logPass("New user message matches expected text");
            } else {
                logFail("New user message validation failed. Expected: [" +
                        expectedMessage + "] but found: [" + newUserMessage + "]");
                Assert.fail("New user message mismatch!");
            }

            logPass("New user message validation flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }
}
