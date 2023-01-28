package io.github.tahanima.pages.product;

import com.microsoft.playwright.Locator;
import io.github.tahanima.pages.BasePage;

/**
 * This class captures only those features needed to support test functionalities of the login page.
 *
 * @author tahanima
 */
public class ProductsPage extends BasePage {
    public Locator getTitleLocator() {
        return page.locator(".title");
    }
}
