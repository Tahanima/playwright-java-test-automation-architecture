package io.github.tahanima.ui.component;

import com.microsoft.playwright.Page;

/**
 * @author tahanima
 */
public final class Header extends BaseComponent {

    public Header(final Page page) {
        super(page);
    }

    public void clickOnHamburgerIcon() {
        page.click("#react-burger-menu-btn");
    }
}
