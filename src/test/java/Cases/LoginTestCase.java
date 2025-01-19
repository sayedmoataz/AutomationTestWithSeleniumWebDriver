package Cases;

import org.metachain.data.BaseOperation;
import static org.metachain.data.DataAndLocators.*;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test suite for Login functionality
 * Prerequisites:
 * - Valid test user credentials configured in config.properties
 * - Test environment accessible and configured
 * - Database in known state
 * - Web drivers properly configured
 */

public class LoginTestCase extends BaseOperation {
    private static final Logger logger = LoggerFactory.getLogger(LoginTestCase.class);

    @BeforeMethod
    public void setUp() {
        try {
            super.setUp();
            logger.info("starting login test setup");
        } catch (Exception e) {
            logger.error("Setup failed: {}", e.getMessage());
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @Test(description = "Verify complete login and logout flow - Happy Path")
    public void testLoginLogoutFlow() {
        try {
            logger.info("starting login-logout flow test");
            navigateToLoginPage();
            performLogin();
            verifyLoginSuccess();
            performLogout();
            verifyLogoutSuccess();
        } catch (Exception e) {
            logger.error("Login-logout flow test failed: {}", e.getMessage());
            throw e;
        }
    }

    @Test(description = "Verify login fails with incorrect password")
    public void testLoginWithIncorrectPassword() {
        try {
            logger.info("Starting incorrect password test");
            navigateToLoginPage();
            fillLoginFormWithIncorrectPassword();
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("invalid credentials");
        } catch (Exception e) {
            logger.error("Incorrect password test failed: {}", e.getMessage());
            throw e;
        }
    }

    @Test(description = "Verify login fails with empty fields")
    public void testLoginWithEmptyFields() {
        try {
            logger.info("Starting empty fields test");
            navigateToLoginPage();
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("Please enter your email.");
        } catch (Exception e) {
            logger.error("Empty fields test failed: {}", e.getMessage());
            throw e;
        }
    }

    @Test(description = "Verify login fails with non-existent user")
    public void testLoginWithNonExistentUser() {
        try {
            logger.info("Starting non-existent user test");
            navigateToLoginPage();
            fillLoginFormWithNonExistentUser();
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("invalid credentials");
        } catch (Exception e) {
            logger.error("Non-existent user test failed: {}", e.getMessage());
            throw e;
        }
    }

    @Test(description = "Verify login fails with invalid email format")
    public void testLoginWithInvalidEmailFormat() {
        try {
            logger.info("Starting invalid email format test");
            navigateToLoginPage();
            fillLoginForm("invalid-email", PASSWORD);
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("Please enter a valid email address.");
        } catch (Exception e) {
            logger.error("Invalid email format test failed: {}", e.getMessage());
            throw e;
        }
    }

    @Test(description = "Verify login fails with SQL injection in both email and password fields")
    public void testLoginWithSqlInjectionInBothFields() {
        try {
            logger.info("starting SQL injection in both fields test");
            navigateToLoginPage();
            fillLoginForm("' OR '1'='1", "' OR '1'='1");
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("Please enter a valid email address.");
            logger.info("SQL injection in both fields test passed: Login failed as expected");
        } catch (Exception e) {
            logger.error("SQL injection in both fields test failed: {}", e.getMessage());
            throw e;
        }
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][] {
                {"wrong@email.com", "wrongpass", "invalid credentials"},
                {"", "", "Please enter your email."},
                {"test@test.com", "", "Please enter your password."},
                {"", "password123", "Please enter your email."},
                {"invalid-email", "password123", "Please enter a valid email address."},
                {"'; DROP TABLE users; --", "admin' --", "Please enter a valid email address."}
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void testInvalidLoginScenarios(String email, String password, String expectedError) {
        try {
            logger.info("test invalid login scenario with email: {}", email);
            navigateToLoginPage();
            fillLoginForm(email, password);
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure(expectedError);
        } catch (Exception e) {
            logger.error("Invalid login scenario failed for email {}: {}", email, e.getMessage());
            throw e;
        }
    }

    private void fillLoginFormWithIncorrectPassword() {
        fillLoginForm(USER_EMAIL, "wrongpassword");
    }

    private void fillLoginFormWithNonExistentUser() {
        fillLoginForm("nonexistent@example.com", PASSWORD);
    }

    private void navigateToLoginPage() {
        try {
            logger.info("Navigating to login page");
            clickElement(GET_STARTED_BUTTON);
            waitForElement(EMAIL_INPUT);
            Assert.assertTrue(isElementPresent(EMAIL_INPUT), "Login page not loaded properly");
        } catch (Exception e) {
            logger.error("Navigation to login page failed: {}", e.getMessage());
            throw new RuntimeException("Failed to navigate to login page", e);
        }
    }

    private void performLogin() {
        try {
            logger.info("Performing login with user: {}", USER_EMAIL);
            fillLoginForm(USER_EMAIL, PASSWORD);
            clickElement(SIGN_IN_BUTTON);
        } catch (Exception e) {
            logger.error("Login failed: {}", e.getMessage());
            throw new RuntimeException("Login failed", e);
        }
    }

    private void fillLoginForm(String email, String password) {
        try {
            waitForElement(EMAIL_INPUT);
            sendKeysToElement(EMAIL_INPUT, email);
            sendKeysToElement(PASSWORD_INPUT, password);
            if (!email.isEmpty() && !password.isEmpty()) {
                checkCheckbox(REMEMBER_ME_CHECKBOX);
            }
        } catch (Exception e) {
            logger.error("Failed to fill login form: {}", e.getMessage());
            throw new RuntimeException("Failed to fill login form", e);
        }
    }

    private void verifyLoginSuccess() {
        try {
            waitForElement(SUCCESS_TOAST);
            assertTextEquals("login passed", SUCCESS_TOAST, LOGIN_SUCCESS_MESSAGE, "Login was not successful");
            logger.info("Login successful");
        } catch (Exception e) {
            logger.error("Login success verification failed: {}", e.getMessage());
            throw new RuntimeException("Failed to verify login success", e);
        }
    }

    private void verifyLoginFailure(String expectedErrorMessage) {
        try {
            waitForElement(SUCCESS_TOAST);
            assertTextEquals("login failure", SUCCESS_TOAST, expectedErrorMessage, "Error message not displayed as expected");
            logger.info("Login failure verified with message: {}", expectedErrorMessage);
        } catch (Exception e) {
            logger.error("Login failure verification failed: {}", e.getMessage());
            throw new RuntimeException("Failed to verify login failure", e);
        }
    }

    private void performLogout() {
        try {
            logger.info("Performing logout");
            waitForElement(USER_MENU_BUTTON);
            clickElement(USER_MENU_BUTTON);
            waitForElement(LOGOUT_BUTTON);
            clickElement(LOGOUT_BUTTON);
        } catch (Exception e) {
            logger.error("Logout failed: {}", e.getMessage());
            throw new RuntimeException("Logout failed", e);
        }
    }

    private void verifyLogoutSuccess() {
        try {
            assertTextEqualsOnNewPage("logout", SIGNIN_PAGE_URL, WELCOME_MESSAGE, WELCOME_BACK_MESSAGE, "Logout verification failed");
            verifyUserIsLoggedOut();
            logger.info("Logout successful");
        } catch (Exception e) {
            logger.error("Logout success verification failed: {}", e.getMessage());
            throw new RuntimeException("Failed to verify logout success", e);
        }
    }

    private void verifyUserIsLoggedOut() {
        try {
            waitForUrlChange(SIGNIN_PAGE_URL);
            Assert.assertTrue(isElementPresent(EMAIL_INPUT), "Login form is not visible after logout");
        } catch (Exception e) {
            logger.error("User logout verification failed: {}", e.getMessage());
            throw new RuntimeException("Failed to verify user is logged out", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            logger.info("Cleaning up test resources");
            super.tearDown();
        } catch (Exception e) {
            logger.error("Teardown failed: {}", e.getMessage());
            throw new RuntimeException("Test teardown failed", e);
        }
    }
}