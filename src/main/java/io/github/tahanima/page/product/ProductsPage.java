package io.github.tahanima.page.product;

import com.microsoft.playwright.Locator;

import io.github.tahanima.page.BasePage;
import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public class ProductsPage extends BasePage {

    @Step
    public Locator getTitle() {
        return page.locator(".title");
    }
}
