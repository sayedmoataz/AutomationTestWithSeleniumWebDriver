package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsDriverManager implements DriverManager {
    @Override
    public WebDriver getDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("javascriptEnabled",true);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                System.getProperty("user.dir") + "/src/main/resources/phantomjs"
        );

        return new PhantomJSDriver(caps);
    }

    @Override
    public void saveCookies(WebDriver driver) {}

    @Override
    public void loadCookies(WebDriver driver) {}
}
