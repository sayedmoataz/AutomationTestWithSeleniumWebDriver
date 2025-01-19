package Cases;

import org.metachain.data.BaseOperation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.metachain.data.DataAndLocators.*;

public class SignupTestCase extends BaseOperation {

    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testSignup(){
        clickElement(GET_STARTED_BUTTON);
        clickElement(SIGN_UP_TEST_BUTTON);
        fillRegistrationForm();
        clickElement(SIGN_UP_LOCATOR);
        assertTextEquals("SignUp",SUCCESS_TOAST, SIGN_UP_SUCCESS_MESSAGE, "Registration success message mismatch!");
    }

    private void fillRegistrationForm() {
        sendKeysToElement(FIRST_NAME_LOCATOR, FIRST_NAME);
        sendKeysToElement(LAST_NAME_LOCATOR, LAST_NAME);
        sendKeysToElement(REGISTER_EMAIL_LOCATOR, REGISTER_EMAIL);
        setPhoneNumber(PHONE_NUMBER_LOCATOR, "123456781", "46");
        sendKeysToElement(NEW_PASSWORD_LOCATOR, NEW_PASSWORD);
        sendKeysToElement(CONFIRM_NEW_PASSWORD_LOCATOR, NEW_PASSWORD);
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}