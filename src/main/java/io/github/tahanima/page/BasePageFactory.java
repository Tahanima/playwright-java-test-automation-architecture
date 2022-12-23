package io.github.tahanima.page;

import com.microsoft.playwright.Page;

/**
 * @author tahanima
 */
public final class BasePageFactory {
    private BasePageFactory() {}

    public static <T extends BasePage> T createInstance(final Page page, final Class<T> basePage) {
        try {
            BasePage instance = basePage.getDeclaredConstructor().newInstance();
            instance.initialize(page);

            return basePage.cast(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Page class instantiation failed.");
    }
}
