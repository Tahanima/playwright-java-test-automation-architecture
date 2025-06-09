package io.github.tahanima.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.github.tahanima.factory.BasePageFactory;
import io.github.tahanima.ui.page.BasePage;
import io.github.tahanima.ui.page.LoginPage;
import io.github.tahanima.util.BrowserManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Optional;

import static io.github.tahanima.report.AllureManager.setAllureEnvironmentInfo;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * @author tahanima
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected LoginPage loginPage;

    @RegisterExtension
    AfterTestExecutionCallback callback =
            context -> {
                Optional<Throwable> exception = context.getExecutionException();

                if (exception.isPresent()) {
                    captureScreenshotOnFailure();
                }
            };

    protected abstract byte[] captureScreenshotOnFailure();

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeAll
    public void setupAll() {
        playwright = Playwright.create();
        browser = BrowserManager.getBrowser(playwright);

        setAllureEnvironmentInfo();
    }

    @AfterAll
    public void teardownAll() {
        browser.close();
        playwright.close();
    }
}
