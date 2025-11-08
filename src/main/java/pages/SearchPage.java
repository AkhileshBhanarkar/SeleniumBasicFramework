package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object class for Search Page functionality
 */
public class SearchPage extends TestBase {

    @FindBy(id = "twotabsearchtextbox")
    WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    WebElement searchButton;

    @FindBy(xpath = "//div[@role='listitem']/div/div")
    List<WebElement> searchResults;

    @FindBy(xpath = "//span[@data-action='a-dropdown-button']")
    WebElement filterDropdown;

    @FindBy(xpath = "//a[contains(@id, 'sort-select')]")
    List<WebElement> filterOptions;

    @FindBy(xpath = "//span[@class='s-pagination-strip']//li")
    public List<WebElement> paginationButtons;

    /**
     * Constructor to initialize page elements
     */
    public SearchPage() {
        PageFactory.initElements(driver, this);
    }

    /**
     * Performs search operation for given keyword
     * @param keyword text to search for
     */
    public void searchForProduct(String keyword) {
        utils.waitForElementToBeDisplayed(searchBox,10);
        searchBox.clear();
        searchBox.sendKeys(keyword);
        utils.waitForElementToBeDisplayed(searchButton,10);
        utils.waitForElementToBeClickable(searchButton,10);
        searchButton.click();
    }

    /**
     * Checks if search results are displayed
     * @return true if results are displayed, false if it doesn't disply
     */
    public boolean isSearchResultDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(searchResults));
        return !searchResults.isEmpty();
    }

    /**
     * Applies filter to search results
     * @param filterOption filter option to be applied
     */
    public void applyFilter(String filterOption) {
        utils.refreshPage();
        utils.waitForElementToBeDisplayed(filterDropdown,10);
        filterDropdown.click();
        for (WebElement filter : filterOptions) {
            utils.waitForElementToBeDisplayed(filter,10);
            if (filter.getText().trim().equalsIgnoreCase(filterOption)) {
                filter.click();
                break;
            }
        }
    }

    /**
     * Verifies if filter is applied to search results
     * @return true if filter is applied, false if filter is not applied
     */
    public boolean isFilterApplied() {
        utils.waitForElementToBeDisplayed(filterDropdown,10);
        return !filterDropdown.getText().trim().isEmpty();
    }

    /**
     * Checks if search result is empty
     * @return true if no search results found, false if soething searched
     */
    public boolean isSearchResultEmpty() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfAllElements(searchResults),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'No results')]")) // Adjust XPath if a specific "No results" element/text exists
            ));
            return searchResults.isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Checks if pagination is present for search results
     * @return true if pagination exists, false if doesn't exists
     */
    public boolean isPaginationPresent() {
        utils.waitForElementToBeDisplayed(paginationButtons.get(0), 10);
        return paginationButtons.size() > 1;
    }

    /**
     * Navigates to specified page number in search results
     * @param pageNumber page number to navigate on that page
     */
    public void navigateToPage(int pageNumber) {
        if (pageNumber <= paginationButtons.size()) {
            paginationButtons.get(pageNumber - 1).click();
            utils.waitForSomeTime(3);
        }
    }
}