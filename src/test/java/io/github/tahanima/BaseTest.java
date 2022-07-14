package io.github.tahanima;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.github.tahanima.browser.BrowserManager;
import io.github.tahanima.page.BasePage;
import io.github.tahanima.page.BasePageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

/**
 * @author tahanima
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
  private final Playwright playwright = Playwright.create();
  private final Browser browser = BrowserManager.browser(playwright);
  private final Page page = browser.newPage();

  public abstract void initialize();

  protected <T extends BasePage> T createInstance(final Class<T> basePage) {
    return BasePageFactory.createInstance(page, basePage);
  }

  @BeforeClass
  public void setup() {
    initialize();
  }

  @AfterClass
  public void teardown() {
    playwright.close();
  }
}
