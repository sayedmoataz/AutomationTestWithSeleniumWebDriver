package Cases;

import org.metachain.data.BaseOperation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.metachain.data.DataAndLocators.*;

public class SignupTestCase extends BaseOperation {
    private static final int DEFAULT_OTP_LENGTH = 6;
    private static final int OTP_WAIT_TIMEOUT = 10;

    @BeforeMethod
    public void setUp() {
        super.setUp();
    }

    @Test(description = "Verify complete user signup flow with email and phone verification")
    public void testCompleteSignupFlow() {
        try {
            completeInitialRegistration();
            verifyEmail();
            verifyPhoneNumber();
            finalizeAccountSetup();
            verifySuccessfulSignup();
        } catch (Exception e) {
            takeScreenshot(driver);
            throw new AssertionError("Signup flow failed: " + e.getMessage());
        }
    }

    private void completeInitialRegistration() {
        try {
            navigateToSignUpPage();
            verifyNavigationURL("/SignUp");
            fillRegistrationForm();
            clickElement(SIGN_UP_LOCATOR);
            verifyNavigationURL("/ConfirmationEmailCode");
        } catch (Exception e) {
            throw new RuntimeException("Failed during initial registration: " + e.getMessage());
        }
    }

    private void verifyEmail() {
        try {
            fillEmailOtp();
            clickElement(VERIFY_OTP_LOCATOR);
            verifyNavigationURL("/ConfirmationPhoneCode");
        } catch (Exception e) {
            throw new RuntimeException("Email verification failed: " + e.getMessage());
        }
    }

    private void verifyPhoneNumber() {
        try {
            setPhoneNumberAndFillOTP();
        } catch (Exception e) {
            throw new RuntimeException("Phone verification failed: " + e.getMessage());
        }
    }

    private void finalizeAccountSetup() {
        try {
            checkCheckbox(ACCEPT_DISCLAIMER);
            clickElement(CREATE_ACCOUNT_BUTTON);
            verifyNavigationURL("/Disclaimer");
            clickElement(SIGN_UP_BUTTON);
            verifyNavigationURL("/SignUpAccountCreated");
        } catch (Exception e) {
            throw new RuntimeException("Account finalization failed: " + e.getMessage());
        }
    }

    private void verifySuccessfulSignup() {
        clickElement(SIGN_UP_BUTTON);
        verifyNavigationURL("/TabNavigator");
    }

    private void navigateToSignUpPage() {
        clickElement(GET_STARTED_BUTTON);
        clickElement(SIGN_UP_TEST_BUTTON);
    }

    private void fillRegistrationForm() {
        Map<By, String> formFields = new HashMap<>();
        formFields.put(FIRST_NAME_LOCATOR, FIRST_NAME);
        formFields.put(LAST_NAME_LOCATOR, LAST_NAME);
        formFields.put(REGISTER_EMAIL_LOCATOR, REGISTER_EMAIL);
        formFields.put(NEW_PASSWORD_LOCATOR, NEW_PASSWORD);
        formFields.put(CONFIRM_NEW_PASSWORD_LOCATOR, NEW_PASSWORD);

        formFields.forEach(this::sendKeysToElement);
        setPhoneNumber(PHONE_NUMBER_LOCATOR, USER_NUMBER, "46");
    }

    private void fillEmailOtp() {
        for (int i = 0; i < OTP_CODE.length(); i++) {
            WebElement otpField = waitForElement(By.id("input-" + i));
            otpField.sendKeys(String.valueOf(OTP_CODE.charAt(i)));
        }
    }

    private void setPhoneNumberAndFillOTP() {
        verifyPhoneNumber(VERIFY_PHONE_LOCATOR, USER_NUMBER, "46");
        clickElement(SEND_SMS_BUTTON);
        verifyNavigationURL("/ConfirmationPhoneCode");
        fillOTP(OTP_CODE);
        clickElement(By.id("verify-btn"));
        verifyNavigationURL("/Disclaimer");
    }

    private void fillOTP(String otp) {
        WebDriverWait otpWait = new WebDriverWait(driver, Duration.ofSeconds(OTP_WAIT_TIMEOUT));
        By otpFieldsLocator = By.cssSelector("div[data-testid='otp-input-root'] input[data-testid='input']");

        List<WebElement> otpFields = otpWait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(otpFieldsLocator)
        );

        if (otpFields.size() != DEFAULT_OTP_LENGTH) {
            throw new IllegalStateException(
                    String.format("Expected %d OTP fields but found %d", DEFAULT_OTP_LENGTH, otpFields.size())
            );
        }

        for (int i = 0; i < otp.length(); i++) {
            WebElement field = otpFields.get(i);
            field.clear();
            field.sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

    private void deleteAccountWithHttpURLConnection() {
        try {
            URL url = new URL("https://api.apple-slice.multitool.ai/api/delete-account");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("X-Password", "9aIk^sQ)3GX00E;F/$4hjQs");
            connection.setDoOutput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Failed to delete account. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (ProtocolException | MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        super.tearDown();
        deleteAccountWithHttpURLConnection();
    }
}