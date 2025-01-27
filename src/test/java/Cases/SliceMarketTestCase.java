package Cases;

import org.metachain.data.LoginUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.metachain.data.DataAndLocators.*;

public class SliceMarketTestCase extends LoginUtils {

    @BeforeMethod
    public void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @Test(description = "Verify product names extracted after login")
    public void testExtractProductNamesAfterLogin() {
        try {
//            navigateToLoginPage();
            performLogin();
            waitForElement(SLICE_MARKET);
            clickElement(SLICE_MARKET);
            Thread.sleep(2000);
            waitForElement(SEARCH_INPUT);
            Thread.sleep(2000);
            WebElement productContainer = driver.findElement(PRODUCT_CONTAINER);
            Thread.sleep(2000);
            List<WebElement> productNames = productContainer.findElements(PRODUCT_Title);
            for (WebElement productName : productNames) {
                System.out.println("Product Name: " + productName.getText());
            }
            Assert.assertFalse(productNames.isEmpty(), "No product names found");

        } catch (Exception e) {
            throw new RuntimeException("testExtractProductNamesAfterLogin failed", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            super.tearDown();
        } catch (Exception e) {
            throw new RuntimeException("Test teardown failed", e);
        }
    }
}