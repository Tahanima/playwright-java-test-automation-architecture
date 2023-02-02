package io.github.tahanima.e2e.login;

import io.github.tahanima.data.login.LoginData;
import io.github.tahanima.e2e.BaseE2ETest;
import io.github.tahanima.pages.login.LoginPage;
import io.github.tahanima.pages.product.ProductsPage;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.github.tahanima.utils.CsvDataProviderUtils.processCsv;

/**
 * @author tahanima
 */
public class LoginE2ETest extends BaseE2ETest {
    private static final String FILE_PATH = "login/login.csv";
    private LoginPage loginPage;

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData(Method testMethod) {
        String testCaseId = testMethod.getAnnotation(Test.class).testName();

        return processCsv(LoginData.class, FILE_PATH, testCaseId);
    }

    @BeforeMethod(alwaysRun = true)
    public void createBrowserContextAndPage() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void captureScreenshotAndCloseBrowserContext(ITestResult result) {
        ITestNGMethod method = result.getMethod();

        if (ITestResult.FAILURE == result.getStatus()) {
            loginPage.captureScreenshot(method.getMethodName());
        }

        browserContext.close();
    }

    @Override
    public void initialize() {}

    @Test(
            testName = "TC-1",
            dataProvider = "loginData",
            groups = {"smoke", "regression"})
    public void testCorrectUserNameAndCorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        ProductsPage productsPage = createInstance(ProductsPage.class);

        assertThat(productsPage.getTitleLocator()).hasText("Products");
    }

    @Test(
            testName = "TC-2",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testIncorrectUserNameAndCorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-3",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testCorrectUserNameAndIncorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-4",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testIncorrectUserNameAndIncorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-5",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testBlankUserName(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);
        loginPage.goTo().enterPassword(loginDto.getPassword()).clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-6",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testBlankPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);
        loginPage.goTo().enterUsername(loginDto.getUserName()).clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(
            testName = "TC-7",
            dataProvider = "loginData",
            groups = {"regression"})
    public void testLockedOutUser(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }
}
