package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverManager implements DriverManager {
    @Override
    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
