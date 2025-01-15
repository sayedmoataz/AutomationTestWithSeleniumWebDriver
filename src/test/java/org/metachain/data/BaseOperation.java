package org.metachain.data;

import org.metachain.drivers.DriverFactory;
import org.metachain.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static org.metachain.data.DataAndLocators.BROWSER_TYPE;
import static org.metachain.data.DataAndLocators.WEBSITE_URL;

public class BaseOperation {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected void setUp() {
        DriverManager driverManager = DriverFactory.getDriverManager(BROWSER_TYPE);
        driver = driverManager.getDriver();
        driver.navigate().to(WEBSITE_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void tearDown() {
        if (driver != null) {
            // driverManager.saveCookies(driver);
            driver.quit();
        }
    }

    protected void clickElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void sendKeysToElement(By locator, String keysToSend) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(keysToSend);
    }

    protected  void clearAndSendKeysToElement(By locator, String keysToSend) {
        WebElement element = driver.findElement(locator);
        element.clear();  // Clear the existing value
        element.sendKeys(keysToSend);  // Send the new value
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void checkCheckbox(By locator) {
        WebElement checkbox = waitForElement(locator);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    protected void assertTextEquals(String testCaseName,By locator, String expectedText, String errorMessage) {
        String actualText = waitForElement(locator).getText();
        Assert.assertEquals(actualText, expectedText, errorMessage);
        System.out.println(testCaseName+ " Passed! \nExpected: '" + expectedText + "' \nActual: '" + actualText + "'");
    }

    protected void assertTextEqualsOnNewPage(String testCaseName,String expectedUrlPart, By locator, String expectedText, String errorMessage) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        String actualText = element.getText();
        Assert.assertEquals(actualText, expectedText, errorMessage);
        System.out.println(testCaseName + " Passed! \nExpected: '" + expectedText + "' \nActual: '" + actualText + "'");
    }

    protected boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void waitForUrlChange(String expectedUrl) {
        wait.until(ExpectedConditions.urlContains(expectedUrl));
    }
}

