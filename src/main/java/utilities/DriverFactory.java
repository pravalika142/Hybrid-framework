package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public enum BrowserType {
        CHROME, FIREFOX, EDGE, SAFARI
    }

    /**
     * Initialize WebDriver based on browser type
     */
    public static WebDriver initializeDriver(String browserName) {
        BrowserType browser;
        try {
            browser = BrowserType.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Browser: {} not found. Using CHROME as default", browserName);
            browser = BrowserType.CHROME;
        }

        WebDriver driverInstance = null;

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driverInstance = new ChromeDriver(getChromeOptions());
                logger.info("Chrome browser initiated");
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driverInstance = new FirefoxDriver(getFirefoxOptions());
                logger.info("Firefox browser initiated");
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                driverInstance = new EdgeDriver(getEdgeOptions());
                logger.info("Edge browser initiated");
                break;

            case SAFARI:
                driverInstance = new SafariDriver();
                logger.info("Safari browser initiated");
                break;

            default:
                logger.warn("Unknown browser: {}. Using CHROME", browserName);
                WebDriverManager.chromedriver().setup();
                driverInstance = new ChromeDriver(getChromeOptions());
        }

        driver.set(driverInstance);
        return driverInstance;
    }

    /**
     * Get Chrome options
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--start-maximized",
            "--disable-notifications",
            "--disable-popup-blocking"
        );

        boolean headless = Boolean.parseBoolean(
            ConfigReader.getProperty("headless")
        );
        if (headless) {
            options.addArguments("--headless");
        }

        return options;
    }

    /**
     * Get Firefox options
     */
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");

        boolean headless = Boolean.parseBoolean(
            ConfigReader.getProperty("headless")
        );
        if (headless) {
            options.addArguments("--headless");
        }

        return options;
    }

    /**
     * Get Edge options
     */
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(
            "--start-maximized",
            "--disable-notifications"
        );

        boolean headless = Boolean.parseBoolean(
            ConfigReader.getProperty("headless")
        );
        if (headless) {
            options.addArguments("--headless");
        }

        return options;
    }

    /**
     * Get the WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driverInstance = driver.get();

        if (driverInstance == null) {
            String browserName = ConfigReader.getProperty("browser");
            initializeDriver(browserName);
            driverInstance = driver.get();
        }

        return driverInstance;
    }

    /**
     * Quit the driver and clean up
     */
    public static void quitDriver() {
        WebDriver driverInstance = driver.get();

        if (driverInstance != null) {
            driverInstance.quit();
            logger.info("Browser quit successfully");
            driver.remove();
        }
    }

}
