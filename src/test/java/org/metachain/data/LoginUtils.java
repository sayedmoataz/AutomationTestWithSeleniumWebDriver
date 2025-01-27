package org.metachain.data;

import static org.metachain.data.DataAndLocators.*;

public class LoginUtils extends BaseOperation {

    public void performLogin() {
        try {
            waitForElement(EMAIL_INPUT);
            fillLoginForm(USER_EMAIL, PASSWORD);
            clickElement(SIGN_IN_BUTTON);
            driver.navigate().to(WEBSITE_URL+ "/overview");
            Thread.sleep(1000);
            verifyNavigationURL("/overview");
        } catch (Exception e) {
            throw new RuntimeException("Login failed", e);
        }
    }

//    protected void navigateToLoginPage() {
//        try {
//            clickElement(GET_STARTED_BUTTON);
//            verifyNavigationURL("/SignIn");
//            waitForElement(EMAIL_INPUT);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to navigate to login page", e);
//        }
//    }

    protected void fillLoginForm(String email, String password) {
        try {
            sendKeysToElement(EMAIL_INPUT, email);
            sendKeysToElement(PASSWORD_INPUT, password);
            if (!email.isEmpty() && !password.isEmpty()) {
                checkCheckbox(REMEMBER_ME_CHECKBOX); // TODO:: don't forget to make soft assertion + show password
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fill login form", e);
        }
    }
}
