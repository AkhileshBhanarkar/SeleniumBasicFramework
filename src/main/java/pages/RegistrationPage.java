package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object class representing Registration Page functionality
 */
public class RegistrationPage extends TestBase {

    @FindBy(xpath = "//h1[@class='a-spacing-small moa_desktop_signup']")
    WebElement registrationHeaderText;
    @FindBy(name = "customerName")
    WebElement customerName;
    @FindBy(name = "password")
    WebElement password;

    /**
     * Constructor to initialize page elements
     */
    public RegistrationPage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Gets the registration page header text
     * @return String for header text
     */
    public String validateRegistrationPageHeaderText() {
        utils.waitForElementToBeDisplayed(registrationHeaderText, 10);
        return registrationHeaderText.getText();
    }

    /**
     * Enters customer name in registration form
     * @param name customer name to be entered
     * @return entered name string
     */
    public String enterCustomerName(String name) {
        utils.waitForElementToBeVisible(customerName, 10);
        utils.waitForElementToBeClickable(customerName, 10);
        customerName.sendKeys(name);
        return name;
    }

    /**
     * Enters password in registration form
     * @param pass password to be entered
     * @return entered password string
     */
    public String enterPassword(String pass) {
        utils.waitForElementToBeVisible(password, 10);
        utils.waitForElementToBeClickable(password, 10);
        password.sendKeys(pass);
        return pass;
    }

}
