package Cases;

import org.metachain.data.BaseOperation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.metachain.data.DataAndLocators.*;


public class LoginTestCase extends BaseOperation {
    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @Test(description = "Verify complete login and logout flow")
    public void testLoginLogoutFlow() {
        // login flow
        navigateToLoginPage();
        performLogin();
        verifyLoginSuccess();

        // logout flow
        performLogout();
        verifyLogoutSuccess();
    }

    private void navigateToLoginPage() {
        clickElement(GET_STARTED_BUTTON);
        waitForElement(EMAIL_INPUT);
    }

    private void performLogin() {
        fillLoginForm();
        clickElement(SIGN_IN_BUTTON);
    }

    private void fillLoginForm() {
        sendKeysToElement(EMAIL_INPUT, USER_EMAIL);
        sendKeysToElement(PASSWORD_INPUT, PASSWORD);
        checkCheckbox(REMEMBER_ME_CHECKBOX);
    }

    private void verifyLoginSuccess() {
        assertTextEquals("login",SUCCESS_TOAST, LOGIN_SUCCESS_MESSAGE, "Login was not successful");
    }

    private void performLogout() {
        clickElement(USER_MENU_BUTTON);
        clickElement(LOGOUT_BUTTON);
    }

    private void verifyLogoutSuccess() {
        assertTextEqualsOnNewPage("logout",SIGNIN_PAGE_URL, WELCOME_MESSAGE, WELCOME_BACK_MESSAGE, "Logout verification failed");
        verifyUserIsLoggedOut();
    }

    private void verifyUserIsLoggedOut() {
        waitForUrlChange(SIGNIN_PAGE_URL);
        Assert.assertTrue(isElementPresent(EMAIL_INPUT), "Login form is not visible after logout");
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
    }
}
