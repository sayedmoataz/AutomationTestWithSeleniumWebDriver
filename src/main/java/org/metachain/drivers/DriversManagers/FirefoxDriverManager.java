package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager implements DriverManager {
    @Override
    public WebDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/resources/geckodriver");
        return new FirefoxDriver();
    }
}
