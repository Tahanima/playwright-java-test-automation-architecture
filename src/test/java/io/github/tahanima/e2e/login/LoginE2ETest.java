package io.github.tahanima.e2e.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import io.github.tahanima.data.login.LoginTestData;
import io.github.tahanima.e2e.BaseE2ETest;
import io.github.tahanima.page.login.LoginPage;
import io.github.tahanima.page.product.ProductsPage;
import io.github.tahanima.util.DataSource;
import io.qameta.allure.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

/**
 * @author tahanima
 */
class LoginE2ETest extends BaseE2ETest {

    private LoginPage loginPage;

    @BeforeEach
    public void initialize() {
        browserContext = browser.newContext();
        page = browserContext.newPage();

        loginPage = createInstance(LoginPage.class);
    }

    @Attachment(value = "Failed Test Case Screenshot", type = "image/png")
    protected byte[] captureScreenshotOnFailure() {
        return loginPage.captureScreenshot();
    }

    @AfterEach
    public void closeBrowserContext() {
        browserContext.close();
    }

    @Tag("smoke")
    @Tag("regression")
    @Feature("Login page")
    @Story("User enters correct login credentials")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets redirected to 'Products' page after submitting correct login credentials")
    @DataSource(testCaseId = "TC-1", filePath = "login/login.csv", clazz = LoginTestData.class)
    void testCorrectLoginCredentials(LoginTestData loginDto) {
        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        ProductsPage productsPage = createInstance(ProductsPage.class);

        assertThat(productsPage.getTitle()).hasText("Products");
    }

    @Tag("regression")
    @Feature("Login page")
    @Story("User enters incorrect login credentials")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting incorrect login credentials")
    @DataSource(testCaseId = "TC-2", filePath = "login/login.csv", clazz = LoginTestData.class)
    void testIncorrectLoginCredentials(LoginTestData loginDto) {
        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Tag("regression")
    @Feature("Login page")
    @Story("User keeps the username blank")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting login credentials where the username is blank")
    @DataSource(testCaseId = "TC-3", filePath = "login/login.csv", clazz = LoginTestData.class)
    void testBlankUserName(LoginTestData loginDto) {
        loginPage
                .navigateToUrl()
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Tag("regression")
    @Feature("Login page")
    @Story("User keeps the password blank")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting login credentials where the password is blank")
    @DataSource(testCaseId = "TC-4", filePath = "login/login.csv", clazz = LoginTestData.class)
    void testBlankPassword(LoginTestData loginDto) {
        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Tag("regression")
    @Feature("Login page")
    @Story("User is locked out")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting login credentials for locked out user")
    @DataSource(testCaseId = "TC-5", filePath = "login/login.csv", clazz = LoginTestData.class)
    void testLockedOutUser(LoginTestData loginDto) {
        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }
}
