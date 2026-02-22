# Project: Playwright Java Test Automation Architecture

## Context & Tech Stack
- **Build Tool:** Gradle (using `build.gradle`)
- **Language:** Java 17+
- **Browser Automation Library:** Playwright
- **Architecture:** Data-driven test automation using POM to model and reuse web page functionality.
- **Base Package:** `io.github.tahanima`
- **Test Runner:** JUnit 5 with custom annotations (`@TestDataSource`, `@Validation`, `@Smoke`, `@Regression`).

---

## Project Mapping
- **Pages:** `src/main/java/io/github/tahanima/ui/page/` – Encapsulate low-level page actions and locators.
- **Components:** `src/main/java/io/github/tahanima/ui/component/` – Reusable UI elements (Header, SideNav, etc.).
- **TestData POJOs:** `src/main/java/io/github/tahanima/testdata/` – Business logic layer that interprets CSV data and interacts with Pages.
- **Tests:** `src/test/java/io/github/tahanima/e2e/` – High-level orchestration; must extend `BaseTest`.
- **Test Data Files:** `src/test/resources/testdata/` – CSV files for data-driven inputs.
- **Utilities:**
    - `BrowserManager.java` – Browser lifecycle management.
    - `CsvLoader.java` – CSV loading for test data.
- **Factories:**
    - `BasePageFactory.java` – Centralized page initialization.
    - `BrowserFactory.java` – Abstracts browser setup and creation.
- **Reporting:** `AllureManager.java` – Custom logging, screenshots, and test reporting.

---

## Development Rules
1. **Instantiation:** Do not use `new LoginPage()`. Always use the `BasePageFactory` to initialize pages.
2. **Logic Placement:**
    - **Pages/Components:** Handle locators and atomic actions (e.g., `typeUsername`).
    - **TestData POJOs:** Handle business workflows (e.g., `loginWith(LoginTestData data)`).
    - **Tests:** Handle assertions and test flow. Minimal direct Playwright API calls here.
3. **Stability & Waits:**
    - **No Hard Sleeps:** Never use `Thread.sleep()`. Rely on Playwright’s auto-waiting or `page.waitForCondition()`.
    - **Web-First Assertions:** Use `PlaywrightAssertions.assertThat()` for UI states to enable automatic retries.
4. **Configuration:** Use `ConfigurationManager` to access `config.properties`. Do not hardcode URLs or credentials.
5. **Data Loading:** Use the custom `@TestDataSource` annotation for CSV data-driven tests.
6. **Naming Conventions:**
    - Pages → `*Page.java`
    - Components → `*Component.java`
    - Test Data POJOs → `*TestData.java` (must extend `BaseTestData`).

---

## Locator Strategy (Priority Order)
1. `page.getByRole()` (Prioritize accessibility-first selectors).
2. `page.getByLabel()` / `page.getByPlaceholder()`.
3. `page.getByTestId()` (Use when `data-testid` attributes are available).
4. CSS/XPath (Use only as a last resort for complex structural selectors).

---

## Test Data Flow
- CSV files → `TestDataArgumentsProvider` → Test method parameters → Test execution.
- POJOs (`testdata` package) represent structured test data for readability and business logic reuse.

---

## Running Tests
- Run all tests: `./gradlew clean test`
- Run a specific test class: `./gradlew test --tests *LoginTest`
- Generate Allure report: `./gradlew allureServe`

---

## Available Skills (AI Toolbelt)
1. **code-reviewer**:
    - **Trigger:** When providing new code or asking for architectural feedback.
    - **Focus:** Enforcing layer separation (Page vs. TestData vs. E2E) and checking Factory usage.
2. **healing-agent**:
    - **Trigger:** When reporting a `TimeoutError`, broken locator, or execution failure.
    - **Focus:** Converting brittle selectors to the "Locator Strategy" priority list above.

### Usage Guidelines
- **Skill Activation:** If a task matches the triggers above, ask for permission to load the skill using `/skills load <name>`.
- **Contextual Awareness:** Before proposing code changes, refer to `src/main/resources/config.properties` for environment-specific settings.