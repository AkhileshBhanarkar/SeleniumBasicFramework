package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Page Object class for Home Page containing web elements and methods
 */

public class HomePage extends TestBase {
    @FindBy(id = "nav-link-accountList")
    WebElement signInButton;
    @FindBy(id = "nav-logo")
    WebElement amazonLogo;
    @FindBy(id = "nav-cart-count")
    WebElement cartCount;
    @FindBy(id = "nav-cart")
    WebElement cartIcon;
    @FindBy(xpath = "//li[@class='nav-li']/div/a")
    List<WebElement> shoppingContainerList;
    @FindBy(xpath = "//a[contains(@href,'deals')]")
    WebElement todaysDealsLocator;
    @FindBy(xpath = "//*[@id='nav-subnav']/a[1]")
    WebElement dealsPageLinkLocator;
    @FindBy(id = "twotabsearchtextbox")
    WebElement searchBox;
    @FindBy(id = "nav-search-submit-button")
    WebElement searchButton;
    @FindBy(xpath = "(//div[@role='listitem'])[1]")
    WebElement firstSearchResult;

    /**
     * Constructor to initialize page elements
     */
    public HomePage(){
        PageFactory.initElements(driver, this);

    }

    /**
     * Clicks on the Sign In button and navigates to Login Page
     * @return LoginPage object
     */
    public LoginPage clickLoginInButton(){
        utils.waitForElementToBeDisplayed(signInButton,10);
        utils.waitForElementToBeClickable(signInButton,10);
        signInButton.click();
        return new LoginPage();
    }

    /**
     * Validates if Amazon logo is displayed on the page
     * @return true if logo is displayed, false if logo not displayed
     */
    public boolean validateAmazonLogo(){
        utils.waitForElementToBeDisplayed(amazonLogo,10);
        return amazonLogo.isDisplayed();
    }

    /**
     * Gets the current cart item count
     * @return number of items in cart
     */
    public int getCartCount(){
        utils.waitForElementToBeDisplayed(cartCount,10);
        return Integer.parseInt(cartCount.getText());
    }

    /**
     * Clicks on cart icon and navigates to Cart Page
     * @return CartPage object
     */
    public CartPage clickCartIcon(){
        utils.waitForElementToBeDisplayed(cartIcon,10);
        utils.waitForElementToBeClickable(cartIcon,10);
        cartIcon.click();
        return new CartPage();
    }

    /**
     * Clicks on Today's Deals link
     */
    public void clickTodaysDeals() {
        utils.waitForElementToBeDisplayed(todaysDealsLocator, 10);
        utils.waitForElementToBeClickable(todaysDealsLocator, 10);
        todaysDealsLocator.click();
    }

    /**
     * Verifies if Deals page is displayed
     * @return true if deals page is displayed, false if it doesn't diplay
     */
    public boolean isDealsPageDisplayed() {
        utils.waitForSomeTime(3);
        if(driver.getCurrentUrl().contains("deals")){
            utils.waitForElementToBeDisplayed(dealsPageLinkLocator,10);
            return dealsPageLinkLocator.isDisplayed();
        }else {
            return false;
        }
    }

    /**
     * Searches for a product using search box
     * @param productName name of the product to search
     */
    public void searchProduct(String productName) {
        searchBox.clear();
        searchBox.sendKeys(productName);
        searchButton.click();
        utils.waitForElementToBeDisplayed(firstSearchResult, 10);
    }
}
