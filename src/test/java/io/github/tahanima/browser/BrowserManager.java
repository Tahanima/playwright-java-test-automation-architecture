package io.github.tahanima.browser;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import io.github.tahanima.browser.BrowserFactory;

import static io.github.tahanima.config.ConfigurationManager.configuration;

/**
 * @author tahanima
 */
public final class BrowserManager {
    private BrowserManager() {}

    public static Browser browser(Playwright playwright) {
        return BrowserFactory.valueOf(configuration().browser().toUpperCase())
                .initialize(playwright);
    }
}
