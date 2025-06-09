package io.github.tahanima.ui.component;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public final class Header extends BaseComponent {

    public Header(final Page page) {
        super(page);
    }

    @Step("Click on the hamburger icon")
    public void clickOnHamburgerIcon() {
        page.click("#react-burger-menu-btn");
    }
}
