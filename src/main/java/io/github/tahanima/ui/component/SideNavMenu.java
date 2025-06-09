package io.github.tahanima.ui.component;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public final class SideNavMenu extends BaseComponent {

    public SideNavMenu(final Page page) {
        super(page);
    }

    @Step("Click on 'Logout' link")
    public void clickOnLogout() {
        page.click("#logout_sidebar_link");
    }
}
