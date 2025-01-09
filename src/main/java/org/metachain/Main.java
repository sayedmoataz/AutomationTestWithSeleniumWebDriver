package org.metachain;

import org.metachain.drivers.DriverFactory;
import org.metachain.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Main {
    private static final String WEBSITE_URL = "https://appleslice.vercel.app";
    private static final String USERNAME = "user";
    private static final String USER_EMAIL = "user@user.user";
    private static final String PASSWORD = "password";
    private static final String USER_NUMBER = "01111111111";
    private static final int COUNTRY_NUMBER_ID = 56;

    public static void main(String[] args) {
        String browserType = "chrome";
        DriverManager driverManager = DriverFactory.getDriverManager(browserType);
        WebDriver driver = driverManager.getDriver();

        try {
            openWebsite(driver);
            performSignup(driver);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during signup: " + e);
        } finally {
            driver.quit();
        }
    }

    private static void openWebsite(WebDriver driver) {
        driver.navigate().to(WEBSITE_URL);
    }

    private static void performSignup(WebDriver driver) throws InterruptedException {
        clickElement(driver, By.cssSelector("#screen > div:nth-child(4) > button"));  // Get Started
        clickElement(driver, By.cssSelector("#screen > main > div:nth-child(6) > p:nth-child(2)"));  // Sign Up

        fillRegistrationForm(driver);

        clickElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[7]/button"));  // Sign Up Button
        Thread.sleep(10000);
    }

    private static void fillRegistrationForm(WebDriver driver) {
        sendKeysToElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[1]/input"), USERNAME);  // First Name
        sendKeysToElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[2]/input"), USERNAME);  // Last Name
        sendKeysToElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[3]/input"), USER_EMAIL);  // Email

        selectCountry(driver);

        sendKeysToElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[4]/div[2]/input"), USER_NUMBER);  // Phone
        sendKeysToElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[5]/input"), PASSWORD);  // Password
        sendKeysToElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[6]/input"), PASSWORD);  // Confirm Password
    }

    private static void selectCountry(WebDriver driver) {
        clickElement(driver, By.xpath("//*[@id=\"screen\"]/main/div[4]/div[2]/div[2]/div"));  // Open Dropdown
        WebElement countryOption = driver.findElement(By.xpath("//ul/li[" + COUNTRY_NUMBER_ID + "]"));
        countryOption.click();  // Select Country
    }

    private static void clickElement(WebDriver driver, By locator) {
        driver.findElement(locator).click();
    }

    private static void sendKeysToElement(WebDriver driver, By locator, String keysToSend) {
        driver.findElement(locator).sendKeys(keysToSend);
    }
}
