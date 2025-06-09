package io.github.tahanima.e2e;

import com.microsoft.playwright.Browser;
import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import io.github.tahanima.annotation.Smoke;
import io.github.tahanima.annotation.TestDataSource;
import io.github.tahanima.annotation.Validation;
import io.github.tahanima.fixture.LoginFixture;
import io.github.tahanima.ui.page.LoginPage;
import io.github.tahanima.ui.page.ProductsPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.github.tahanima.config.ConfigurationManager.config;

/**
 * @author tahanima
 */
@Feature("Login Test")
public class LoginTest extends BaseTest {

    private static final String CSV_PATH = "login.csv";
    private static final String VIDEO_PATH = "login/";

    @BeforeEach
    public void setupEach(TestInfo testInfo) {
        String testMethodName =
                (testInfo.getTestMethod().isPresent())
                        ? testInfo.getTestMethod().get().getName()
                        : "";

        if (config().video()) {
            browserContext =
                    browser.newContext(
                            new Browser.NewContextOptions()
                                    .setRecordVideoDir(
                                            Paths.get(
                                                    config().baseTestVideoPath()
                                                            + VIDEO_PATH
                                                            + testMethodName)));
        } else {
            browserContext = browser.newContext();
        }

        page = browserContext.newPage();
        loginPage = createInstance(LoginPage.class);
    }

    @Attachment(value = "Failed Test Case Screenshot", type = "image/png")
    protected byte[] captureScreenshotOnFailure() {
        return loginPage.captureScreenshot();
    }

    @AfterEach
    public void teardownEach() {
        browserContext.close();
    }

    @Smoke
    @Story("User enters correct login credentials")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets redirected to 'Products' page after submitting correct login credentials")
    @ParameterizedRepeatedIfExceptionsTest
    @TestDataSource(id = "TC-1", fileName = CSV_PATH, clazz = LoginFixture.class)
    public void testCorrectLoginCredentials(final LoginFixture fixture) {
        ProductsPage productsPage = loginPage.loginAs(fixture.getUsername(), fixture.getPassword());

        assertThat(productsPage.getTitle()).hasText("Products");
    }

    @Validation
    @Story("User enters incorrect login credentials")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting incorrect login credentials")
    @ParameterizedRepeatedIfExceptionsTest
    @TestDataSource(id = "TC-2", fileName = CSV_PATH, clazz = LoginFixture.class)
    public void testIncorrectLoginCredentials(final LoginFixture fixture) {
        loginPage.loginAs(fixture.getUsername(), fixture.getPassword());

        assertThat(loginPage.getErrorMessage()).hasText(fixture.getErrorMessage());
    }

    @Validation
    @Story("User keeps the username blank")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting login credentials where the username is blank")
    @ParameterizedRepeatedIfExceptionsTest
    @TestDataSource(id = "TC-3", fileName = CSV_PATH, clazz = LoginFixture.class)
    public void testBlankUserName(final LoginFixture fixture) {
        loginPage.open().typePassword(fixture.getPassword()).submitLogin();

        assertThat(loginPage.getErrorMessage()).hasText(fixture.getErrorMessage());
    }

    @Validation
    @Story("User keeps the password blank")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting login credentials where the password is blank")
    @ParameterizedRepeatedIfExceptionsTest
    @TestDataSource(id = "TC-4", fileName = CSV_PATH, clazz = LoginFixture.class)
    public void testBlankPassword(final LoginFixture fixture) {
        loginPage.open().typeUsername(fixture.getUsername()).submitLogin();

        assertThat(loginPage.getErrorMessage()).hasText(fixture.getErrorMessage());
    }

    @Validation
    @Story("User is locked out")
    @Owner("Tahanima Chowdhury")
    @Description(
            "Test that verifies user gets error message after submitting login credentials for locked out user")
    @ParameterizedRepeatedIfExceptionsTest
    @TestDataSource(id = "TC-5", fileName = CSV_PATH, clazz = LoginFixture.class)
    public void testLockedOutUser(final LoginFixture fixture) {
        loginPage.loginAs(fixture.getUsername(), fixture.getPassword());

        assertThat(loginPage.getErrorMessage()).hasText(fixture.getErrorMessage());
    }
}
