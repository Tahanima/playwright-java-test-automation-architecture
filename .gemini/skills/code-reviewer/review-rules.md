# Architecture Review Rules

## 1. UI Layer (Pages & Components)
- **Inheritance:** `BasePage` must be extended by all pages; `BaseComponent` by all components.
- **Locators:** Must be `private`. Use `page.getByRole`, `page.getByLabel`, or `page.getByTestId`.
- **Method Scope:** Page methods should return `void` or a new Page instance (Fluent interface).
- **Separation:** Ensure `Header` or `SideNavMenu` elements are not duplicated in `LoginPage`. They must stay in the `component` package.

## 2. Test Data Layer (The Bridge)
- **Role:** Review `LoginTestData` or `ProductsTestData`. These should act as POJOs or Logic wrappers that interpret CSV data for the UI.
- **Factory usage:** Ensure these classes don't instantiate pages using `new`. They should rely on the `BasePageFactory`.

## 3. E2E Test Layer
- **Inheritance:** All tests must extend `BaseTest`.
- **Annotations:**
    - Mandatory: `@Epic`, `@Feature`, and `@Story` (Allure).
    - Mandatory: Category annotations like `@Smoke` or `@Regression`.
- **Data Driven:** - Verify `@ParameterizedTest` is used with `@TestDataSource(fileName = "xxx.csv")`.
    - Flag any hardcoded test data strings.
- **Assertions:** Use Playwright's `assertThat()` for auto-retrying. Avoid generic JUnit `Assertions.assertEquals()` for UI elements.

## 4. Framework Consistency
- **Configuration:** Use `ConfigurationManager.configuration()` to access properties; never use `System.getProperty` directly.
- **Logging:** Use `AllureManager` for any custom logging or attachments within the test flow.
- **Clean Code:** - No `Thread.sleep()`.
    - No `System.out.println()` (Use Allure steps).