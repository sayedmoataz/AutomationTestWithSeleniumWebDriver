package Cases;

import org.metachain.data.LoginUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.metachain.data.DataAndLocators.*;

public class WalletTestCase extends LoginUtils {
    @BeforeMethod
    public void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @Test(description = "Check if there are transactions and extract data")
    public void testWalletScreenAndCheckButtons() {
        try {
//            navigateToLoginPage();
            performLogin();
            waitForElement(WALLET);
            clickElement(WALLET);
            Thread.sleep(2000);
            System.out.println("is deposit button exist? " +isElementPresent(DEPOSIT_BUTTON));
            Assert.assertTrue(isElementPresent(DEPOSIT_BUTTON), "Deposit button is not present");
            System.out.println("is withdraw button exist? " +isElementPresent(WITHDRAW_BUTTON));
            Assert.assertTrue(isElementPresent(WITHDRAW_BUTTON), "Withdraw button is not present");
        } catch (Exception e) {
            throw new RuntimeException("testWalletScreenAndCheckButtons failed", e);
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
