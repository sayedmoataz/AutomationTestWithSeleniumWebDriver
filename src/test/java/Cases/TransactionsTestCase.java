package Cases;

import org.metachain.data.LoginUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.metachain.data.DataAndLocators.*;

public class TransactionsTestCase extends LoginUtils {
    @BeforeMethod
    public void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @Test(description = "Check if there are transactions and extract data")
    public void testTransactionCountAndExtractLast() {
        try {
//            navigateToLoginPage();
            performLogin();
            waitForElement(TRANSACTIONS);
            clickElement(TRANSACTIONS);
            Thread.sleep(2000);
            List<WebElement> transactionDivs = driver.findElements(TRANSACTIONS_CONTAINER);
            if (!transactionDivs.isEmpty()) {
                System.out.println("There are " + transactionDivs.size() + " transactions.");
                for (WebElement transaction : transactionDivs) {
                    Thread.sleep(500);
                    String transactionData = extractData(transaction);
                    System.out.println("Data from transaction: " + transactionData);
                    Assert.assertNotNull(transactionData, "Transaction data should not be null.");
                }
            } else {
                System.out.println("There are no transactions.");
                Assert.assertTrue(true, "There are no transactions.");
            }
        } catch (Exception e) {
            throw new RuntimeException("testTransactionCountAndExtractLast failed", e);
        }
    }

    private String extractData(WebElement transactionElement) {
        WebElement h5Element = transactionElement.findElement(TRANSACTIONS_Provider);
        return h5Element.getText();
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
