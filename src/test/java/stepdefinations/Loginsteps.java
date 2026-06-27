package stepdefinations;

import base.BaseTest;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import org.testng.Assert;
import utilities.DriverFactory;
import pages.LoginPage;
import java.util.Map;

/**
 * LoginSteps - Step Definitions using Page Object Mode
 * Improvements over direct Selenium code:
 * 1. Clean and readable steps
 * 2. Locators hidden in LoginPage
 * 3. Easy to maintain - change LoginPage, not all tests
 * 4. Reusable across multiple test classes
 */
public class Loginsteps extends BaseTest {

    private LoginPage loginPage;

    /**
     * STEP 1: Navigate to login page
     * - Creates LoginPage instance
     * - Calls navigateToLoginPage() method from POM
     */
    @Given("User is on the login page")
    public void user_is_on_the_login_page() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.navigateToLoginPage();
        
        // Verify page loaded successfully
        Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page failed to load");
    }

    /**
     * STEP 2: Submit login credentials
     * - Extracts data from DataTable (feature file)
     * - Uses LoginPage.enterUsername() and enterPassword()
     *
     * DataTable format:
     *   | username | password  |
     *   | Admin    | admin123  |
     */
    @When("User submits login credentials with")
    public void user_submits_login_credentials_with(DataTable dataTable) {
        // Convert DataTable to List of Maps
        java.util.List<Map<String, String>> credentials = dataTable.asMaps(String.class, String.class);
        
        // Get first row of data
        Map<String, String> data = credentials.get(0);
        String username = data.get("username");
        String password = data.get("password");
        
        // Use POM methods instead of direct Selenium
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    /**
     * STEP 3: Click login button
     * - Calls LoginPage.clickLoginButton()
     * - POM handles all waits and interactions
     */
    @When("User clicks login button")
    public void user_clicks_login_button() {
        loginPage.clickLoginButton();
    }

    /**
     * STEP 4: Verify successful login
     * - Calls LoginPage.isUserLoggedIn() for verification
     * - AssertTrue fails test if login unsuccessful
     */
    @Then("User should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        Assert.assertTrue(loginPage.isUserLoggedIn(), "❌ User is not logged in successfully");
    }
}






