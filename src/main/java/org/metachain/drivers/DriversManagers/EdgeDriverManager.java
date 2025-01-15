package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager implements DriverManager {
    @Override
    public WebDriver getDriver() {
        System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/src/main/resources/msedgedriver");
        return new EdgeDriver();
    }

    @Override
    public void saveCookies(WebDriver driver) {}

    @Override
    public void loadCookies(WebDriver driver) {}
}
