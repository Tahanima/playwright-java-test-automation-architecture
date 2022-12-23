package io.github.tahanima.page.product;

import io.github.tahanima.page.BasePage;

/**
 * This class captures only those features needed to support test functionalities of the login page.
 *
 * @author tahanima
 */
public class ProductsPage extends BasePage {
    public String getTitle() {
        return page.locator(".title").textContent();
    }
}
