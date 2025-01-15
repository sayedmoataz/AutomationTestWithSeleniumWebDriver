package org.metachain.drivers;

import org.metachain.drivers.DriversManagers.*;

public class DriverFactory {
    public static DriverManager getDriverManager(String browserType) {
        return switch (browserType.toLowerCase()) {
            case "chrome" -> new ChromeDriverManager();
            case "firefox" -> new FirefoxDriverManager();
            case "safari" -> new SafariDriverManager();
            case "edge" -> new EdgeDriverManager();
            case "headless" -> new PhantomJsDriverManager();
            default -> throw new IllegalArgumentException("unsupported browser type: " + browserType);
        };
    }
}
