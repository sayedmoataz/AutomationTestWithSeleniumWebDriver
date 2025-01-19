package org.metachain.data;

import org.metachain.drivers.DriverFactory;
import org.metachain.drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.metachain.data.DataAndLocators.BROWSER_TYPE;
import static org.metachain.data.DataAndLocators.WEBSITE_URL;

public class BaseOperation {

    protected static WebDriver driver;
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

    protected void setPhoneNumber(By locator, String phoneNumber, String countryCode) {
        WebElement element = driver.findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            WebElement flagDropdown = driver.findElement(By.className("flag-dropdown"));
            flagDropdown.click();
            Thread.sleep(100);
            flagDropdown.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        element.clear();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        js.executeScript("arguments[0].value = '';", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        if (countryCode != null && !countryCode.isEmpty()) {
            if (!countryCode.startsWith("+")) {
                countryCode = "+" + countryCode;
            }
            element.sendKeys(countryCode);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (char c : phoneNumber.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        wait.until(driver -> {
            String actualValue = (String) js.executeScript(
                    "return arguments[0].value.replace(/\\D/g, '');", element
            );
            String expectedValue = phoneNumber.replaceAll("\\D", "");
            return actualValue.endsWith(expectedValue);
        });
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
        if(isElementVisible(locator)){
            String actualText = waitForElement(locator).getText();
            takeScreenshot(driver);
            Assert.assertEquals(actualText, expectedText, errorMessage);
            System.out.println(testCaseName+ " Passed! \nExpected: '" + expectedText + "' \nActual: '" + actualText + "'");
        } else {
            System.out.print("toast doesn't appeared");
        }
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

    protected void takeScreenshot(WebDriver driver) {
        try {
            String projectPath = System.getProperty("user.dir");
            Path screenshotsDir = Paths.get(projectPath, "screenshots");
            Files.createDirectories(screenshotsDir);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path screenshotPath = screenshotsDir.resolve("screenshot_" + timestamp + ".png");
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), screenshotPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved to: " + screenshotPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

