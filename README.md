# Selenium Web Automation Project

A Java-based Selenium WebDriver project that demonstrates automated web testing using the Page Factory design pattern.
This project specifically automates the signup process for a web application.

## Features

- Multi-browser support (Chrome, Firefox, Safari)
- Factory design pattern implementation for WebDriver management
- Modular and maintainable code structure
- Automated signup process demonstration

## Prerequisites

Before running this project, make sure you have the following installed:

- Java JDK 23 or higher
- Maven
- Chrome/Firefox/Safari browser
- Corresponding WebDriver executables:
    - ChromeDriver
    - GeckoDriver (for Firefox)
    - SafariDriver (comes built-in with macOS)
    - EdgeDriver

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── metachain/
│   │           ├── drivers/
│   │           │   ├── DriversManagers/
│   │           │   │   ├── ChromeDriverManager.java
│   │           │   │   ├── FirefoxDriverManager.java
│   │           │   │   └── SafariDriverManager.java
│   │           │   ├── DriverFactory.java
│   │           │   └── DriverManager.java
│   │           └── Main.java
│   └── resources/
│       ├── chromedriver
│       └── geckodriver
```

## Configuration

Update the following constants in `Main.java` according to your needs:

```java
private static final String WEBSITE_URL = "YOUR_URL";
private static final String USERNAME = "user";
private static final String USER_EMAIL = "user@user.user";
private static final String PASSWORD = "password";
private static final String USER_NUMBER = "01111111111";
private static final int COUNTRY_NUMBER_ID = 56; // from custom dropbox
```

## Running the Tests

1. To run with a different browser, modify the `browserType` variable in `Main.java`:

```java
String browserType = "firefox"; // or "safari"
```

## Implementation Details

### Design Patterns Used

1. **Factory Pattern**: Implemented through `DriverFactory` to manage different browser drivers
2. **Strategy Pattern**: Used in the driver management system to handle different browser implementations

### Key Components

- `DriverManager`: Interface defining the contract for browser driver implementations
- `DriverFactory`: Factory class for creating browser-specific driver instances
- `ChromeDriverManager`, `FirefoxDriverManager`, `SafariDriverManager`: Concrete implementations for different browsers

## Dependencies

- Selenium WebDriver 4.27.0
- JUnit 3.8.1 (for testing)
- Maven for dependency management
