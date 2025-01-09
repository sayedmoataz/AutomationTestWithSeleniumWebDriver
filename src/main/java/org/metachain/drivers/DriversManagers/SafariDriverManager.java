package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverManager implements DriverManager {
    @Override
    public WebDriver getDriver() {
        return new SafariDriver();
    }
}
