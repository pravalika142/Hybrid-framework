package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.ConfigReader;
import java.time.Duration;

/**
 * LoginPage - Page Object Model (POM) for OrangeHRM Login
 * POM Benefits:
 * 1. Centralized locators - easy to maintain
 * 2. Reusable methods - used across multiple test classes
 * 3. Better readability - step definitions become cleaner
 * 4. Easy updates - change locators in one place only
 * Structure:
 * - Locators (By objects) - Web element identifiers
 * - WebDriver & WebDriverWait - For browser interaction & waits
 * - Constructor - Initialize page with driver
 * - Action methods - User interactions (click, sendKeys, etc.)
 * - Verification methods - Assert/check page elements
 */
public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ===== LOCATORS =====
    // Using private By constants makes locators easy to update
    // If OrangeHRM changes UI, update only these locators

    /** Username input field locator */
    private final By usernameField = By.name("username");

    /** Password input field locator */
    private final By passwordField = By.name("password");

    /** Login submit button locator */
    private final By loginButton = By.xpath("//button[@type='submit']");

    /** Dashboard topbar - appears after successful login */
    private final By dashboardTopbar = By.xpath("//div[@class='oxd-topbar']");

    /** User profile dropdown - proof of successful login */
    private final By userDropdown = By.xpath("//span[@class='oxd-userdropdown-tab']");

    /** Logout button - appears in user menu */
    private final By logoutButton = By.xpath("//a[text()='Logout']");

    // ===== CONSTRUCTOR =====

    /**
     * Initialize LoginPage with WebDriver
     * Called whenever we want to interact with login page
     *
     * @param driver WebDriver instance from DriverFactory
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Get explicit wait duration from testData.properties
        int waitTime = Integer.parseInt(ConfigReader.getProperty("explicit_wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }

    // ===== PAGE ACTION METHODS =====

    /**
     * Navigate to login page URL
     * - Gets URL from ConfigReader (testData.properties)
     * - Waits for username field to be present
     */
    public void navigateToLoginPage() {
        String url = ConfigReader.getUrl();
        driver.navigate().to(url);
        waitForPageLoad();
        System.out.println("✓ Navigated to login page: " + url);
    }

    /**
     * Enter username in username field
     *
     * @param username username to enter (e.g., "Admin")
     */
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField))
            .sendKeys(username);
        System.out.println("✓ Username entered: " + username);
    }

    /**
     * Enter password in password field
     *
     * @param password password to enter (e.g., "admin123")
     */
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        System.out.println("✓ Password entered");
    }

    /**
     * Click the login/submit button
     * - Waits for button to be clickable
     * - Handles potential stale element exceptions
     */
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
            .click();
        System.out.println("✓ Login button clicked");
    }

    /**
     * Complete login flow - convenience method
     * Combines username entry, password entry, and button click
     *
     * @param username username to login
     * @param password password to login
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // ===== PAGE VERIFICATION METHODS =====

    /**
     * Verify that login page is loaded
     * Checks if username field is visible
     *
     * @return true if login page is ready
     */
    public boolean isLoginPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            System.out.println("✓ Login page is loaded");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Login page failed to load: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify that user is logged in successfully
     * Checks if dashboard topbar and user dropdown are visible
     *
     * @return true if user is logged in
     */
    public boolean isUserLoggedIn() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(dashboardTopbar));
            boolean hasUserDropdown = driver.findElements(userDropdown).size() > 0;

            if (hasUserDropdown) {
                System.out.println("✓ User successfully logged in!");
                return true;
            }
        } catch (Exception e) {
            System.out.println("❌ Login verification failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Wait for page to fully load
     * Ensures username field is present before proceeding
     */
    private void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
    }

    /**
     * Get current page URL
     * Useful for assertions in tests
     *
     * @return current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get page title
     * Useful for verifying page transitions
     *
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
