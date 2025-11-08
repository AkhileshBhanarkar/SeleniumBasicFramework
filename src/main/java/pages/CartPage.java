package pages;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object class for Cart Page functionality
 */
public class CartPage extends TestBase {

    @FindBy(xpath = "//div[@id='sc-empty-cart']/descendant::h3")
    WebElement emptyCartMessage;
    @FindBy(xpath = "//a[contains(@href,'ref=cart_empty_sign_in')]")
    WebElement signInButton;
    @FindBy(xpath = "(//div[@role='listitem']//descendant::button[@name='submit.addToCart'])[1]")
    WebElement addToCartButtonOfFirstSearchResult;
    @FindBy(id = "nav-cart-count")
    WebElement cartCount;
    @FindBy(xpath = "//input[@value='Delete']")
    List<WebElement> deleteButtons;
    @FindBy(name = "proceedToRetailCheckout")
    WebElement checkoutButton;
    @FindBy(id="nav-cart-count-container")
    WebElement cartButton;



    /**
     * Constructor to initialize page elements
     */
    public CartPage(){
        PageFactory.initElements(driver, this);
    }

    /**
     * Gets the empty cart message text
     * @return String that contains empty cart message
     */
    public String getEmptyCartMessage(){
        utils.waitForElementToBeVisible(emptyCartMessage,10);
        return emptyCartMessage.getText();
    }

    /**
     * Checks if the cart is empty
     * @return true if cart is empty, false is cart contains items
     */
    public boolean isCartEmpty() {
        try {
            utils.waitForElementToBeDisplayed(emptyCartMessage,10);
            return emptyCartMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Removes all items from the cart
     */
    public void clearCart() {
        utils.waitForElementToBeVisible(deleteButtons.get(0), 10);
        utils.scrollToElement(deleteButtons.get(0));
        while (!deleteButtons.isEmpty()) {
            deleteButtons.get(0).click();
            utils.waitForSomeTime(2);
        }
    }

    /**
     * Adds first search result item to cart
     */
    public void addItemToCart() {
        utils.scrollToElement(addToCartButtonOfFirstSearchResult);
        utils.waitForElementToBeDisplayed(addToCartButtonOfFirstSearchResult, 10);
        addToCartButtonOfFirstSearchResult.click();
        utils.waitForSomeTime(2);
        utils.scrollToElement(cartCount);
        utils.waitForElementToBeVisible(cartCount, 10);
    }

    /**
     * Gets the number of items in cart
     * @return integer for number of items
     */
    public int getCartItemCount() {
        utils.waitForElementToBeVisible(cartCount, 10);
        utils.waitForElementToBeDisplayed(cartCount, 10);
        return Integer.parseInt(cartCount.getText());
    }

    /**
     * Clicks checkout button and verifies the navigation
     * @return true if navigation to signin page
     */
    public boolean isCheckoutButtonClicked(){
        utils.waitForElementToBeClickable(checkoutButton, 10);
        checkoutButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.urlContains("signin"));
    }

    /**
     * Opens the cart view
     */
    public void viewCart(){
        utils.waitForElementToBeClickable(cartButton,10);
        cartButton.click();
        utils.waitForElementToBeDisplayed(checkoutButton,10);
    }

}
