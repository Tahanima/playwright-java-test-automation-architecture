# Architecture Review Rules (Enhanced for Agentic Verification)

## 1. UI Layer (Pages & Components)
- **Inheritance:** `BasePage` must be extended by all pages; `BaseComponent` by all components.
- **Locators:** Must be `private`.
- **Strategy (Priority):** Use `page.getByRole`, `page.getByLabel`, or `page.getByTestId`. 
  - *Agent Rule:* If using MCP, verify that the `AriaRole` matches the element's actual role in the browser.
- **Method Scope:** Page methods should return `void` or a new Page instance (Fluent interface).
- **Separation:** Use MCP to confirm if an element belongs in a Shared Component (e.g., `Header`) or a specific Page.

## 2. Test Data Layer (The Bridge)
- **Role:** Review `LoginTestData` or `ProductsTestData`. These act as POJOs/Logic wrappers for CSV data.
- **Factory usage:** Ensure these classes don't instantiate pages using `new`. Use `BasePageFactory`.

## 3. E2E Test Layer
- **Inheritance:** All tests must extend `BaseTest`.
- **Annotations:**
    - Mandatory: `@Epic`, `@Feature`, and `@Story` (Allure).
    - Mandatory: Category annotations like `@Smoke` or `@Regression`.
- **Data Driven:** Verify `@ParameterizedTest` is used with `@TestDataSource`.
- **Assertions:** Use Playwright's `assertThat()` for auto-retrying. 

## 4. Framework & Live Verification
- **Configuration:** Access properties via `ConfigurationManager.configuration()`.
- **Uniqueness:** If MCP is active, verify that locators do not return multiple elements (Strict Mode compliance).
- **Clean Code:**
  - ❌ No `Thread.sleep()`.
  - ❌ No hardcoded URLs in classes (use config).
  - ✅ Use `AllureManager` for custom attachments/logs.
