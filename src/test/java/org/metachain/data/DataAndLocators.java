package org.metachain.data;

import org.openqa.selenium.By;

public class DataAndLocators {
    // bases
    public static final String BROWSER_TYPE = "chrome";
//    public static final String WEBSITE_URL = "https://appleslice.vercel.app";
    public static final String WEBSITE_URL = "https://apple-slice.vercel.app/";

    // login
    public static final By EMAIL_INPUT = By.name("email");
    public static final By PASSWORD_INPUT = By.name("password");
    public static final By REMEMBER_ME_CHECKBOX = By.xpath("//*[@id=\"screen\"]/main/form/div[3]/button");
    public static final By SIGN_IN_BUTTON = By.xpath("//*[@id=\"screen\"]/main/form/div[4]/button");


    public static final By GET_STARTED_BUTTON = By.xpath("//*[@id=\"screen\"]/div[4]/button");
    public static final By SUCCESS_TOAST = By.cssSelector(".styleToast");

    // sign up
    public static final By SIGN_UP_TEST_BUTTON = By.xpath("//*[@id=\"screen\"]/main/div[5]/p[2]");
    public static final By FIRST_NAME_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[1]/input");
    public static final By LAST_NAME_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[2]/input");
    public static final By REGISTER_EMAIL_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[3]/input");
    public static final By PHONE_NUMBER_LOCATOR = By.name("phone");
    public static final By NEW_PASSWORD_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[5]/input");
    public static final By CONFIRM_NEW_PASSWORD_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[6]/input");
    public static final By SIGN_UP_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[7]/button");
    public static final By VERIFY_OTP_LOCATOR = By.xpath("//*[@id=\"screen\"]/main/div[3]/button");
    public static final By VERIFY_PHONE_LOCATOR = By.className("form-control");
    public static final By ACCEPT_DISCLAIMER = By.xpath("//*[@id=\"screen\"]/main/div[2]/label/input");
    public static final By CREATE_ACCOUNT_BUTTON = By.xpath("//*[@id=\"screen\"]/main/div[3]/button");
    public static final By SIGN_UP_BUTTON = By.xpath("//*[@id=\"screen\"]/main/div/button");
    public static final By SEND_SMS_BUTTON = By.id("signup-btn");

    // user menu & logout locators
    public static final By USER_MENU_BUTTON = By.xpath("//*[@id=\"app\"]/div[2]/header/div[3]/div/p");
    public static final By LOGOUT_BUTTON = By.xpath("//*[@id=\"screen\"]/main/div[3]/button");

    // navigator
    public static final By SLICE_MARKET = By.xpath("//*[@id=\"screen\"]/nav/div/button[3]");
    public static final By SEARCH_INPUT = By.xpath("//*[@id=\"screen\"]/main/div[1]/div");
    public static final By PRODUCT_CONTAINER = By.cssSelector("div.swiper-wrapper");
    public static final By PRODUCT_Title = By.cssSelector("p.productName");
    public static final By TRANSACTIONS = By.xpath("//*[@id=\"screen\"]/nav/div/button[2]");
    public static final By TRANSACTIONS_CONTAINER = By.cssSelector("div[style*='background-color: rgb(255, 238, 222)']");
    public static final By TRANSACTIONS_Provider = By.tagName("h5");
    public static final By WALLET = By.xpath("//*[@id=\"screen\"]/nav/div/button[4]");
    public static final By DEPOSIT_BUTTON = By.xpath("//*[@id=\"screen\"]/main/div[1]/div[2]/button");
    public static final By WITHDRAW_BUTTON = By.xpath("//*[@id=\"screen\"]/main/div[1]/div[1]/button");

    // Test data
    public static final String USER_EMAIL = "khaled.mofed2@gmail.com";
    public static final String PASSWORD = "qweQWE123";

    public static final String FIRST_NAME = "sayed";
    public static final String LAST_NAME = "sayed";
    public static final String REGISTER_EMAIL = "sayed@test.com";
    public static final String NEW_PASSWORD = "123456789";
    public static final String USER_NUMBER = "123456701";
    public static final String OTP_CODE = "123456";
}
