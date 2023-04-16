package io.github.tahanima.e2e.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import static io.github.tahanima.util.DataProviderUtils.processCsv;

import io.github.tahanima.data.login.LoginTestData;
import io.github.tahanima.e2e.BaseE2ETest;
import io.github.tahanima.page.login.LoginPage;
import io.github.tahanima.page.product.ProductsPage;

import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * @author tahanima
 */
public class LoginE2ETest extends BaseE2ETest {
    private static final String FILE_PATH = "login/login.csv";
    private LoginPage loginPage;

    @DataProvider(name = "loginData")
    public Object[][] getLoginData(Method testMethod) {
        String testCaseId = testMethod.getAnnotation(Test.class).testName();

        return processCsv(LoginTestData.class, getTestDataFilePath(FILE_PATH), testCaseId);
    }

    @BeforeMethod(alwaysRun = true)
    public void createBrowserContextAndPage() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void captureScreenshotOnFailureAndCloseBrowserContext(ITestResult result) {
        ITestNGMethod method = result.getMethod();

        if (ITestResult.FAILURE == result.getStatus()) {
            loginPage.captureScreenshot(
                    String.format(
                            "%s_%s_%s",
                            method.getRealClass().getSimpleName(),
                            method.getMethodName(),
                            method.getParameterInvocationCount()));
        }

        browserContext.close();
    }

    @Test(
            testName = "TC-1",
            dataProvider = "loginData",
            groups = {"smoke", "regression"})
    public void testCorrectUserNameAndCorrectPassword(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        ProductsPage productsPage = createInstance(ProductsPage.class);

        assertThat(productsPage.getTitle()).hasText("Products");
    }

    @Test(
            testName = "TC-2",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testIncorrectUserNameAndCorrectPassword(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-3",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testCorrectUserNameAndIncorrectPassword(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-4",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testIncorrectUserNameAndIncorrectPassword(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-5",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testBlankUserName(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);
        loginPage
                .navigateToUrl()
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-6",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testBlankPassword(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);
        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-7",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testLockedOutUser(LoginTestData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .navigateToUrl()
                .fillUsernameInTextBox(loginDto.getUserName())
                .fillPasswordInTextBox(loginDto.getPassword())
                .clickOnLoginButton();

        assertThat(loginPage.getErrorMessage()).hasText(loginDto.getErrorMessage());
    }
}
