package io.github.tahanima.e2e;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.github.tahanima.page.BasePage;
import io.github.tahanima.page.BasePageFactory;
import io.github.tahanima.util.BrowserManager;
import io.github.tahanima.util.TestListener;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * @author tahanima
 */
@Listeners(TestListener.class)
public class BaseE2ETest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    protected String getTestDataFilePath(String path) {
        return config().baseTestDataPath() + path;
    }

    protected String getScreenshotFilePath(String path) {
        return config().baseScreenshotPath() + path;
    }

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        playwright = Playwright.create();
        browser = BrowserManager.browser(playwright);
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        browser.close();
        playwright.close();
    }
}
