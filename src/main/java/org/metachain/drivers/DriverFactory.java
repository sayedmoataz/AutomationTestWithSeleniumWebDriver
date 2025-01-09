package org.metachain.drivers;

import org.metachain.drivers.DriversManagers.ChromeDriverManager;
import org.metachain.drivers.DriversManagers.FirefoxDriverManager;
import org.metachain.drivers.DriversManagers.SafariDriverManager;

public class DriverFactory {
    public static DriverManager getDriverManager(String browserType) {
        return switch (browserType.toLowerCase()) {
            case "chrome" -> new ChromeDriverManager();
            case "firefox" -> new FirefoxDriverManager();
            case "safari" -> new SafariDriverManager();
            default -> throw new IllegalArgumentException("unsupported browser type: " + browserType);
        };
    }
}
