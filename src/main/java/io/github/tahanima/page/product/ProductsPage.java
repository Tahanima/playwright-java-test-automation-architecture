package io.github.tahanima.page.product;

import com.microsoft.playwright.Locator;

import io.github.tahanima.page.BasePage;

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
