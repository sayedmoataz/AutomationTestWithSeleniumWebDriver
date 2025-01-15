package org.metachain.drivers;

import org.openqa.selenium.WebDriver;

public interface DriverManager {
    WebDriver getDriver();
    void saveCookies(WebDriver driver);
    void loadCookies(WebDriver driver);
}
