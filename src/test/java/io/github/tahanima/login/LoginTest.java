package io.github.tahanima.login;

import io.github.tahanima.BaseTest;
import io.github.tahanima.data.login.LoginData;
import io.github.tahanima.page.login.LoginPage;
import io.github.tahanima.page.product.ProductsPage;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.github.tahanima.util.CsvDataProviderUtil.processCsv;

/**
 * @author tahanima
 */
public class LoginTest extends BaseTest {
    private static final String FILE_PATH = "login/login.csv";
    private LoginPage loginPage;

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData(Method testMethod) {
        String testCaseId = testMethod.getAnnotation(Test.class).testName();

        return processCsv(LoginData.class, FILE_PATH, testCaseId);
    }

    @BeforeMethod
    public void createBrowserContextAndPage() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @AfterMethod
    public void closeBrowserContext() {
        browserContext.close();
    }

    @Override
    public void initialize() {}

    @AfterMethod
    public void captureScreenshot(ITestResult result) {
        ITestNGMethod method = result.getMethod();

        if (ITestResult.FAILURE == result.getStatus()) {
            loginPage.captureScreenshot(method.getMethodName());
        }
    }

    @Test(testName = "TC-1", dataProvider = "loginData")
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

    @Test(testName = "TC-2", dataProvider = "loginData")
    public void testIncorrectUserNameAndCorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(testName = "TC-3", dataProvider = "loginData")
    public void testCorrectUserNameAndIncorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(testName = "TC-4", dataProvider = "loginData")
    public void testIncorrectUserNameAndIncorrectPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);

        loginPage
                .goTo()
                .enterUsername(loginDto.getUserName())
                .enterPassword(loginDto.getPassword())
                .clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(testName = "TC-5", dataProvider = "loginData")
    public void testBlankUserName(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);
        loginPage.goTo().enterPassword(loginDto.getPassword()).clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(testName = "TC-6", dataProvider = "loginData")
    public void testBlankPassword(LoginData loginDto) {
        loginPage = createInstance(LoginPage.class);
        loginPage.goTo().enterUsername(loginDto.getUserName()).clickLogin();

        assertThat(loginPage.getErrorMessageLocator()).hasText(loginDto.getErrorMessage());
    }

    @Test(testName = "TC-7", dataProvider = "loginData")
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
