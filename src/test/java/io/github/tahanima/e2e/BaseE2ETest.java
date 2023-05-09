package io.github.tahanima.e2e;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

import static io.github.tahanima.config.ConfigurationManager.config;

import static org.junit.jupiter.api.TestInstance.*;

import com.google.common.collect.ImmutableMap;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.tahanima.page.BasePage;
import io.github.tahanima.page.BasePageFactory;
import io.github.tahanima.util.BrowserManager;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Optional;

/**
 * @author tahanima
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class BaseE2ETest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

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
    public void setup() {
        playwright = Playwright.create();
        browser = BrowserManager.browser(playwright);

        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Platform", System.getProperty("os.name"))
                        .put("Version", System.getProperty("os.version"))
                        .put("Browser", StringUtils.capitalize(config().browser()))
                        .put("Context URL", config().baseUrl())
                        .build(),
                config().allureResultsDir() + "/");
    }

    @AfterAll
    public void teardown() {
        browser.close();
        playwright.close();
    }
}
