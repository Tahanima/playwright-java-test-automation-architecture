package io.github.tahanima.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.github.tahanima.browser.BrowserManager;
import io.github.tahanima.pages.BasePage;
import io.github.tahanima.pages.BasePageFactory;
import io.github.tahanima.utils.TestListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * @author tahanima
 */
@Listeners(TestListener.class)
public abstract class BaseE2ETest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    public abstract void initialize();

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        playwright = Playwright.create();
        browser = BrowserManager.browser(playwright);

        initialize();
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        browser.close();
        playwright.close();
    }
}
