import org.metachain.drivers.DriverFactory;
import org.metachain.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class TestNGReporting {
    private static final String browserType = "chrome";
    private static final String WEBSITE_URL = "https://appleslice.vercel.app";
    private static final String FIRST_NAME = "S@yed";
    private static final String LAST_NAME = "S@yed";
    private static final String USER_EMAIL = "satz9@gmail.com";
    private static final String PASSWORD = "sayed123456";
    private static final String USER_NUMBER = "01111001122";
    private static final int COUNTRY_NUMBER_ID = 56;

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverManager driverManager = DriverFactory.getDriverManager(browserType);
        driver = driverManager.getDriver();
        driver.navigate().to(WEBSITE_URL);
    }

    @Test
    public void testSignup() throws InterruptedException {
        clickElement(By.cssSelector("#screen > div:nth-child(4) > button"));  // Get Started
        clickElement(By.cssSelector("#screen > main > div:nth-child(6) > p:nth-child(2)"));  // Sign Up

        // Get the form fields before filling them
        WebElement firstNameField = driver.findElement(By.xpath("//*[@id=\"screen\"]/main/div[1]/input"));
        WebElement lastNameField = driver.findElement(By.xpath("//*[@id=\"screen\"]/main/div[2]/input"));

        // Fill the form
        fillRegistrationForm();

        // test optional cases
        SoftAssert softAssert = new SoftAssert();
        String firstName = firstNameField.getAttribute("value");
        String lastName = lastNameField.getAttribute("value");

        softAssert.assertNotEquals(firstName.trim(), lastName.trim(), "First name and last name should not be identical");

        clickElement(By.xpath("//*[@id=\"screen\"]/main/div[7]/button"));  // Sign Up Button

        // test required case
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popupMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".styleToast")));
        String expectedMessage = "User registered successfully";


        Assert.assertEquals(popupMessage.getText(), expectedMessage, "Registration success message mismatch!");

        softAssert.assertAll();
    }

    private void fillRegistrationForm() {
        sendKeysToElement(By.xpath("//*[@id=\"screen\"]/main/div[1]/input"), FIRST_NAME);  // First Name
        sendKeysToElement(By.xpath("//*[@id=\"screen\"]/main/div[2]/input"), LAST_NAME);  // Last Name
        sendKeysToElement(By.xpath("//*[@id=\"screen\"]/main/div[3]/input"), USER_EMAIL);  // Email

        selectCountry();

        sendKeysToElement(By.xpath("//*[@id=\"screen\"]/main/div[4]/div[2]/input"), USER_NUMBER);  // Phone
        sendKeysToElement(By.xpath("//*[@id=\"screen\"]/main/div[5]/input"), PASSWORD);  // Password
        sendKeysToElement(By.xpath("//*[@id=\"screen\"]/main/div[6]/input"), PASSWORD);  // Confirm Password
    }

    private void selectCountry() {
        clickElement(By.xpath("//*[@id=\"screen\"]/main/div[4]/div[2]/div[2]/div"));  // Open Dropdown
        WebElement countryOption = driver.findElement(By.xpath("//ul/li[" + COUNTRY_NUMBER_ID + "]"));
        countryOption.click();  // Select Country
    }

    private void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    private void sendKeysToElement(By locator, String keysToSend) {
        driver.findElement(locator).sendKeys(keysToSend);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}