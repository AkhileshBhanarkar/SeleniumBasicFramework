package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;

public class CartPageTest extends TestBase {

    CartPage cartPage;
    HomePage homePage;

    public CartPageTest() {
        super(); // Calls TestBase constructor
    }

    @BeforeMethod
    public void setup() {
        initializeDriver(); // Initialize the driver from TestBase
        cartPage = new CartPage();
        homePage = new HomePage();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1)
    public void verifyEmptyCartMessageTest() {
        try {
            test = extent.createTest("Verify Empty Cart Message Test");
            logInfo("Starting verifyEmptyCartMessageTest");

            cartPage = homePage.clickCartIcon();
            logPass("Navigated to cart page");

            String emptyCartMessage = cartPage.getEmptyCartMessage();
            String expectedMessage = "Your Amazon Cart is empty";

            if (emptyCartMessage.equals(expectedMessage)) {
                logPass("Empty cart message verified: [" + emptyCartMessage + "]");
            } else {
                logFail("Empty cart message verification failed. Expected: [" + expectedMessage +
                        "] but found: [" + emptyCartMessage + "]");
                Assert.fail("Empty cart message mismatch!");
            }

            logPass("Empty cart message validation completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 2)
    public void verifyEmptyCartTest() {
        try {
            test = extent.createTest("Verify Empty Cart Test");
            logInfo("Starting verifyEmptyCartTest");

            cartPage = homePage.clickCartIcon();
            logPass("Navigated to cart page");

            boolean isCartEmpty = cartPage.isCartEmpty();

            if (isCartEmpty) {
                logPass("Cart empty state verified successfully");
            } else {
                logFail("Cart empty state verification failed: Cart contains items");
                Assert.fail("Cart is not empty!");
            }

            logPass("Empty cart verification completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 3)
    public void addItemToCartTest() {
        try {
            test = extent.createTest("Verify Add Item To Cart Test");
            logInfo("Starting addItemToCartTest");

            homePage.searchProduct(properties.getProperty("product_1"));
            logPass("Searched for product: " + properties.getProperty("product_1"));

            cartPage.addItemToCart();
            logPass("Attempted to add product to cart");

            int cartItemCount = cartPage.getCartItemCount();

            if (cartItemCount == 1) {
                logPass("Cart item count verified: " + cartItemCount);
            } else {
                logFail("Cart item count verification failed. Expected: 1 but found: " + cartItemCount);
                Assert.fail("Cart count mismatch after adding item!");
            }

            logPass("Add item to cart flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 4)
    public void removeItemFromCartTest() {
        try {
            test = extent.createTest("Verify Remove Item From Cart Test");
            logInfo("Starting removeItemFromCartTest");

            homePage.searchProduct(properties.getProperty("product_1"));
            logPass("Searched for product: " + properties.getProperty("product_1"));

            cartPage.addItemToCart();
            logPass("Added product to cart");

            cartPage.viewCart();
            logPass("Navigated to cart view");

            cartPage.clearCart();
            logPass("Attempted to clear cart");

            int cartItemCount = cartPage.getCartItemCount();

            if (cartItemCount == 0) {
                logPass("Cart is empty after item removal");
            } else {
                logFail("Cart item removal verification failed. Items still present in cart");
                Assert.fail("Cart not empty after removal!");
            }

            logPass("Remove item from cart flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 5)
    public void addProductAndProceedToCheckoutTest() {
        try {
            test = extent.createTest("Verify Add Product And Proceed To Checkout Test");
            logInfo("Starting addProductAndProceedToCheckoutTest");

            homePage.searchProduct(properties.getProperty("product_1"));
            logPass("Searched for product: " + properties.getProperty("product_1"));

            cartPage.addItemToCart();
            logPass("Added product to cart");

            cartPage.viewCart();
            logPass("Navigated to cart view");

            boolean isCheckoutButtonClicked = cartPage.isCheckoutButtonClicked();

            if (isCheckoutButtonClicked) {
                logPass("Successfully initiated checkout process");
            } else {
                logFail("Failed to proceed to checkout: Checkout button not clickable");
                Assert.fail("Checkout button not clicked!");
            }

            logPass("Add product and checkout flow completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 6)
    public void validateMultiItemCartTest() {
        try {
            test = extent.createTest("Verify Multiple Items Added To Cart Test");
            logInfo("Starting validateMultiItemCartTest");

            homePage.searchProduct(properties.getProperty("product_1"));
            logPass("Searched for first product: " + properties.getProperty("product_1"));
            cartPage.addItemToCart();
            logPass("Added first product to cart");

            homePage.searchProduct(properties.getProperty("product_2"));
            logPass("Searched for second product: " + properties.getProperty("product_2"));
            cartPage.addItemToCart();
            logPass("Added second product to cart");

            cartPage.viewCart();
            logPass("Navigated to cart view");

            int cartItemCount = cartPage.getCartItemCount();

            if (cartItemCount == 2) {
                logPass("Cart contains expected number of items: " + cartItemCount);
            } else {
                logFail("Multi-item cart verification failed. Expected: 2 items but found: " + cartItemCount);
                Assert.fail("Cart item count mismatch!");
            }

            logPass("Multi-item cart validation completed successfully");
        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

}

