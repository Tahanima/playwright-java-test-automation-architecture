package io.github.tahanima;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.github.tahanima.browser.BrowserManager;
import io.github.tahanima.page.BasePage;
import io.github.tahanima.page.BasePageFactory;
import io.github.tahanima.util.TestListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * @author tahanima
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    public abstract void initialize();

    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = BrowserManager.browser(playwright);

        initialize();
    }

    @AfterClass
    public void teardown() {
        browser.close();
        playwright.close();
    }
}
