package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object class representing Login Page functionality
 */
public class LoginPage extends TestBase {

    @FindBy(id = "ap_email_login")
    WebElement emailId;
    @FindBy(id = "continue")
    WebElement continueButton;
    @FindBy(xpath = "//h1[@class='a-size-medium-plus a-spacing-small']")
    WebElement signInOrCreateAccountHeaderText;
    @FindBy(xpath = "//div[@id='invalid-email-alert']/div/div")
    WebElement invalidEmailAlert;
    @FindBy(xpath = "//div[@id='invalid-phone-alert']/div/div")
    WebElement invalidPhoneNumberAlertText;
    @FindBy(xpath = "//h1[@class='a-spacing-small']")
    WebElement signInHeaderText;
    @FindBy(xpath = "//div[@id='intent-confirmation-container']/h1")
    WebElement newUserMessage;
    @FindBy(xpath = "//input[@class='a-button-input']")
    WebElement createAccountButton;

    /**
     * Constructor to initialize page elements
     */
    public LoginPage(){
        PageFactory.initElements(driver, this);
    }

    /**
     * Gets the sign in or create account header text
     * @return String for header text
     */
    public String validateSignInOrCreateAccountHeaderText(){
        utils.waitForElementToBeVisible(signInOrCreateAccountHeaderText,10);
        utils.waitForElementToBeDisplayed(signInOrCreateAccountHeaderText,10);
    return signInOrCreateAccountHeaderText.getText();
    }

    /**
     * Gets the sign in page header text
     * @return String for header text
     */
    public String validateSignInPageHeaderText(){
        utils.waitForElementToBeDisplayed(signInHeaderText,10);
        return signInHeaderText.getText();
    }

    /**
     * Performs login with given username
     * @param un email/phone to login with
     */
    public void login(String un){
        utils.waitForElementToBeVisible(emailId,10);
        utils.waitForElementToBeDisplayed(emailId,10);
        emailId.sendKeys(un);
        utils.waitForElementToBeClickable(continueButton,10);
        continueButton.click();
    }

    /**
     * Gets invalid email alert message
     * @return String containing alert message
     */
    public String getInvalidEmailAlertText(){
        utils.waitForElementToBeDisplayed(invalidEmailAlert,10);
        return invalidEmailAlert.getText().trim();
    }

    /**
     * Gets invalid phone number alert message
     * @return String containing alert message
     */
    public String getInvalidPhoneNumberAlertText(){
        utils.waitForElementToBeDisplayed(invalidPhoneNumberAlertText,10);
        return invalidPhoneNumberAlertText.getText().trim();
    }

    /**
     * Gets message shown to new users
     * @return String containing new user message
     */
    public String getNewUserMessage(){
        utils.waitForElementToBeDisplayed(newUserMessage,10);
        return newUserMessage.getText().trim();
    }

    /**
     * Attempts login with new user mobile number
     * @param mobileNumber mobile number to login with
     */
    public void loginWithNewUser(String mobileNumber){
        utils.waitForElementToBeVisible(emailId,10);
        emailId.sendKeys(mobileNumber);
        continueButton.click();
    }

    /**
     * Checks if create account button is displayed
     * @return true if button is displayed, false if it doesn't displi
     */
    public boolean isCreateAccountButtonDisplayed(){
        utils.waitForElementToBeDisplayed(createAccountButton,10);
        return createAccountButton.isDisplayed();
    }

    /**
     * Clicks on create account button
     */
    public void clickCreateAccountButton(){
        utils.waitForElementToBeDisplayed(createAccountButton,10);
        utils.scrollToElement(createAccountButton);
        createAccountButton.click();
    }

}
