package io.github.tahanima.e2e;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Browser;

import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import io.github.tahanima.annotation.Smoke;
import io.github.tahanima.annotation.TestDataSource;
import io.github.tahanima.testdata.ProductsTestData;
import io.github.tahanima.ui.page.LoginPage;
import io.qameta.allure.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Paths;

/**
 * @author tahanima
 */
@Feature("Products Test")
public class ProductsTest extends BaseTest {

    private static final String CSV_PATH = "products.csv";
    private static final String VIDEO_PATH = "products/";

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
    @Story("Logging out from Products page should redirect to Login page")
    @Owner("Tahanima Chowdhury")
    @Description("Test that verifies user gets redirected to 'Login' page after logging out")
    @ParameterizedRepeatedIfExceptionsTest
    @TestDataSource(id = "TC-1", fileName = CSV_PATH, clazz = ProductsTestData.class)
    public void testSuccessfulLogout(ProductsTestData testData) {
        loginPage.loginAs(testData.getUsername(), testData.getPassword()).clickOnLogout();

        assertThat(page).hasURL(testData.getUrl());
    }
}
