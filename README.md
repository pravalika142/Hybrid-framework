
# AMB_TestNGAutomation

## TestNG Automation Framework

This project is a TestNG + Maven based automation framework supporting:
- Multiple environments (QA / Prod)
- Multiple execution modes (Local / Grid)
- Multiple browsers (Chrome / Firefox / Edge)
- Smoke & Regression suites
- Allure reporting

---

## 📋 Prerequisites

- Java 19
- Maven 3.8+
- IntelliJ IDEA
- Browsers:
    - Google Chrome
    - Mozilla Firefox
    - Microsoft Edge
- Selenium Grid (optional, for Grid execution)

---

## 📁 Project Structure

```
YourProject/
│
├── pom.xml
├── testng.xml
│
├── src
│   ├── main
│   │   ├── java
│   │   │
│   │   │   ├── base
│   │   │   │   └── BasePage.java         <-- Common Selenium actions
│   │   │   │
│   │   │   ├── pages
│   │   │   │   ├── LoginPage.java        <-- Page Object for Login
│   │   │   │   └── HomePage.java         <-- Page Object for Home
│   │   │   │
│   │   │   ├── utilities
│   │   │   │   ├── DriverFactory.java    <-- Initialize WebDriver
│   │   │   │   ├── ConfigReader.java     <-- Read properties file
│   │   │   │   ├── ExcelUtil.java        <-- Read Excel test data
│   │   │   │   └── LogManager.java       <-- Logging
│   │   │   │
│   │   │   └── constants
│   │   │       └── FrameworkConstants.java  <-- Framework constants (paths, etc.)
│   │   │
│   │   └── resources
│   │       └── config.properties        <-- Framework config (URL, browser, waits)
│   │
│   └── test
│       ├── java
│       │
│       │   ├── stepdefinitions
│       │   │   ├── LoginSteps.java       <-- Steps for login.feature
│       │   │   ├── SignupSteps.java      <-- Steps for signup.feature
│       │   │   └── CommonSteps.java      <-- Common reusable steps
│       │   │
│       │   ├── hooks
│       │   │   └── Hooks.java            <-- @Before / @After scenarios
│       │   │
│       │   ├── runners
│       │   │   ├── TestRunner.java       <-- Main test runner
│       │   │   ├── SmokeRunner.java      <-- Smoke test runner
│       │   │   └── RegressionRunner.java <-- Regression runner
│       │   │
│       │   └── reports
│       │       └── ExtentManager.java    <-- Extent Report setup
│       │
│       └── resources
│           ├── features
│           │   ├── login.feature         <-- Gherkin scenarios for login
│           │   ├── signup.feature        <-- Gherkin scenarios for signup
│           │   └── common.feature        <-- Reusable feature steps
│           │
│           ├── testdata
│           │   └── LoginData.xlsx        <-- Excel file with test data
│           │
│           └── cucumber.properties       <-- Optional Cucumber config
│
└── reports
    ├── html                          <-- HTML reports
    └── extent                        <-- Extent reports

```

---

## 🚀 Getting Started

1. Clone the repository
2. Install dependencies: `mvn clean install`
3. Update `config.properties` with your environment details
4. Run tests: `mvn test`

---

## 🧪 Running Tests

**Local Execution:**
```bash
mvn clean test -Dbrowser=chrome -Denv=local -DsuiteEnv=QA -DsuiteType=smoke 
```

**Grid Execution:**
```bash
mvn clean test -Dbrowser=chrome -Denv=grid  "-Dcucumber.filter.tags=@smoke" -DAMB_GRID_URL=http://192.168.0.237:4444/wd/hub
```

**Api Tests:**
```bash

mvn clean test -Dbrowser=firefox -Denv=local  "-Dcucumber.filter.tags=@api"
.....

**Specific Suite:**
```bash
mvn test -Dsuite=smoke
```

---

## 📊 Reporting

Reports are generated using Allure. View reports:
```bash
allure serve allure-results
```

---

## 📝 License

Add your license information here.
```

