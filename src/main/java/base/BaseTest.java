package base;

import org.openqa.selenium.WebDriver;
import utilities.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    public BaseTest() {
        this.driver = DriverFactory.getDriver();
    }

    public void quitDriver() {
        DriverFactory.quitDriver();
    }

}
