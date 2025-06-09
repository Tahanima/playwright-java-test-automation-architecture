package io.github.tahanima.ui.page;

import com.microsoft.playwright.Locator;
import io.github.tahanima.factory.BasePageFactory;
import io.github.tahanima.ui.component.Header;
import io.github.tahanima.ui.component.SideNavMenu;
import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public final class ProductsPage extends BasePage {

    private Header header;
    private SideNavMenu sideNavMenu;

    @Override
    public void initComponents() {
        header = new Header(page);
        sideNavMenu = new SideNavMenu(page);
    }

    public Locator getTitle() {
        return page.locator(".title");
    }

    @Step("Click on 'Logout' link from side navigation menu")
    public LoginPage clickOnLogout() {
        header.clickOnHamburgerIcon();
        sideNavMenu.clickOnLogout();
        attachScreenshotOnReport("After clicking on the logout link");

        return BasePageFactory.createInstance(page, LoginPage.class);
    }
}
