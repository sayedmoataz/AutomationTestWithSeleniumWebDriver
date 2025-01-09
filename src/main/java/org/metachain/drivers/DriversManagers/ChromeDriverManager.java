package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager implements DriverManager {
    @Override
    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver");
        return new ChromeDriver();
    }
}
