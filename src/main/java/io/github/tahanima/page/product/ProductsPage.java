package io.github.tahanima.page.product;

import io.github.tahanima.page.BasePage;

/**
 * @author tahanima
 */
public class ProductsPage extends BasePage {
    public String getTitle() {
        return page.locator(".title").textContent();
    }
}
