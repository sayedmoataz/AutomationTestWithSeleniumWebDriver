package org.metachain.drivers.DriversManagers;

import org.metachain.drivers.DriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.Set;

public class ChromeDriverManager implements DriverManager {
    private static final String COOKIE_FILE_PATH = "cookies.data";

    @Override
    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver");
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        // return new ChromeDriver(options);
        return new ChromeDriver();
    }

    @Override
    public void saveCookies(WebDriver driver) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COOKIE_FILE_PATH))) {
            oos.writeObject(driver.manage().getCookies());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadCookies(WebDriver driver) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COOKIE_FILE_PATH))) {
            Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
