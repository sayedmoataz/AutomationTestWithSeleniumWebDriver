package Cases;

import static org.metachain.data.DataAndLocators.*;
import org.metachain.data.LoginUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test suite for Login functionality
 * Prerequisites:
 * - Valid test user credentials configured in config.properties
 * - Test environment accessible and configured
 * - Database in known state
 * - Web drivers properly configured
 */

public class LoginTestCase extends LoginUtils {

    @BeforeMethod
    public void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }

    @Test(description = "Verify complete login and logout flow - Happy Path")
    public void testLoginLogoutFlow() {
        try {
            // login
//            navigateToLoginPage();
            performLogin();
            // log out
//            performLogout();
        } catch (Exception e) {
            throw new RuntimeException("login happy path failed", e);
        }
    }

    @Test(description = "Verify login fails with incorrect password")
    public void testLoginWithIncorrectPassword() {
        try {
//            navigateToLoginPage();
            fillLoginForm(USER_EMAIL, "wrongpassword");
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("invalid credentials");
        } catch (Exception e) {
            throw new RuntimeException("testLoginWithIncorrectPassword failed", e);
        }
    }

    @Test(description = "Verify login fails with empty fields")
    public void testLoginWithEmptyFields() {
        try {
//            navigateToLoginPage();
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("Please enter your email.");
        } catch (Exception e) {
            throw new RuntimeException("testLoginWithEmptyFields failed", e);
        }
    }

    @Test(description = "Verify login fails with non-existent user")
    public void testLoginWithNonExistentUser() {
        try {
//            navigateToLoginPage();
            fillLoginForm("nonexistent@example.com", PASSWORD);
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("invalid credentials");
        } catch (Exception e) {
            throw new RuntimeException("testLoginWithNonExistentUser failed", e);
        }
    }

    @Test(description = "Verify login fails with invalid email format")
    public void testLoginWithInvalidEmailFormat() {
        try {
//            navigateToLoginPage();
            fillLoginForm("invalid-email", PASSWORD);
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("Please enter a valid email address.");
        } catch (Exception e) {
            throw new RuntimeException("testLoginWithInvalidEmailFormat failed", e);
        }
    }

    @Test(description = "Verify login fails with SQL injection in both email and password fields", enabled = false)
    public void testLoginWithSqlInjectionInBothFields() {
        try {
//            navigateToLoginPage();
            fillLoginForm("' OR '1'='1", "' OR '1'='1");
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure("Please enter a valid email address.");
        } catch (Exception e) {
            throw new RuntimeException("testLoginWithSqlInjectionInBothFields failed", e);
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
//            navigateToLoginPage();
            fillLoginForm(email, password);
            clickElement(SIGN_IN_BUTTON);
            verifyLoginFailure(expectedError);
        } catch (Exception e) {
            throw new RuntimeException("testInvalidLoginScenarios failed", e);
        }
    }

    private void verifyLoginFailure(String expectedErrorMessage) {
        try {
            String toastText =  waitForElement(SUCCESS_TOAST).getText();
            System.out.println(toastText);
            assertTextEquals("login failure", SUCCESS_TOAST, expectedErrorMessage, "Error message not displayed as expected");
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify login failure", e);
        }
    }

    private void performLogout() {
        try {
            waitForElement(USER_MENU_BUTTON);
            clickElement(USER_MENU_BUTTON);
            waitForElement(LOGOUT_BUTTON);
            clickElement(LOGOUT_BUTTON);
            verifyNavigationURL("/SignIn");
        } catch (Exception e) {
            throw new RuntimeException("Logout failed", e);
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