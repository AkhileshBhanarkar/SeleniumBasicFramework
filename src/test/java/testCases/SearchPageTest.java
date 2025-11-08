package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SearchPage;

public class SearchPageTest extends TestBase {

    SearchPage searchPage;

    public SearchPageTest() {
        super(); // Calls TestBase constructor
    }

    @BeforeMethod
    public void setup() {
        initializeDriver();
        searchPage = new SearchPage();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1)
    public void searchForProductForValidItemTest() {
        try {
            test = extent.createTest("Search for Valid Item Test");
            logInfo("Starting searchForValidItemTest");

            searchPage.searchForProduct(properties.getProperty("product_1"));
            logPass("Searched for " + properties.getProperty("product_1") + " item");

            boolean isSearchResultDisplayed = searchPage.isSearchResultDisplayed();
            if (isSearchResultDisplayed) {
                logPass("Search results are visible on the page");
            } else {
                logFail("Search results visibility check failed: Results not found on page");
                Assert.fail("Search results were not displayed!");
            }

            logPass("Valid item search flow completed successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 2)
    public void searchForProductForInvalidItemTest() {
        try {
            test = extent.createTest("Search for Invalid Item Test");
            logInfo("Starting searchForInvalidItemTest");

            searchPage.searchForProduct(properties.getProperty("invalid_product_name"));
            logPass("Searched with invalid input");

            boolean isSearchResultEmpty = searchPage.isSearchResultEmpty();
            if (isSearchResultEmpty) {
                logPass("No results displayed as expected for invalid search");
            } else {
                logFail("Invalid search test failed: Unexpected results found for invalid query");
                Assert.fail("Search returned results for invalid query!");
            }

            logPass("Invalid item search flow completed successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 3)
    public void validateSortingTest() {
        try {
            test = extent.createTest("Validate Sorting Options Test");
            logInfo("Starting validateSortingTest");

            searchPage.searchForProduct(properties.getProperty("product_1"));
            logPass("Searched for " + properties.getProperty("product_1") + " item");

            searchPage.applyFilter("Price: Low to High");
            boolean isLowToHighApplied = searchPage.isFilterApplied();
            if (isLowToHighApplied) {
                logPass("Price: Low to High sorting applied successfully");
            } else {
                logFail("Sorting validation failed: Price: Low to High not applied");
                Assert.fail("Sorting by 'Price: Low to High' failed.");
            }

            searchPage.applyFilter("Price: High to Low");
            boolean isHighToLowApplied = searchPage.isFilterApplied();
            if (isHighToLowApplied) {
                logPass("Price: High to Low sorting applied successfully");
            } else {
                logFail("Sorting validation failed: Price: High to Low not applied");
                Assert.fail("Sorting by 'Price: High to Low' failed.");
            }

            logPass("Sorting options validation completed successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

    @Test(priority = 4)
    public void validatePaginationTest() {
        try {
            test = extent.createTest("Validate Pagination Test");
            logInfo("Starting validatePaginationTest");

            searchPage.searchForProduct(properties.getProperty("product_1"));
            logPass("Searched for " + properties.getProperty("product_1") + " item");

            int paginationCount = searchPage.paginationButtons.size();
            if (paginationCount > 1) {
                logPass("Found " + paginationCount + " pagination buttons");
            } else {
                logFail("Pagination validation failed: Pagination not implemented");
                Assert.fail("Pagination is not implemented correctly.");
            }

            boolean isPaginationPresent = searchPage.isPaginationPresent();
            if (isPaginationPresent) {
                logPass("Pagination is present and functional");
            } else {
                logFail("Pagination validation failed: Pagination not found");
                Assert.fail("Pagination not found.");
            }

            searchPage.navigateToPage(2);
            logPass("Successfully navigated to page 2");

            logPass("Pagination validation flow completed successfully");

        } catch (Throwable t) {
            utils.handleTestFailure(t,test);
            throw t;
        }
    }

}