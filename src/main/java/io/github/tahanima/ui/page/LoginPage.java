package io.github.tahanima.ui.page;

import com.microsoft.playwright.Locator;
import io.github.tahanima.factory.BasePageFactory;
import io.qameta.allure.Step;

import static io.github.tahanima.config.ConfigurationManager.config;

/**
 * @author tahanima
 */
public final class LoginPage extends BasePage {

    @Step("Navigate to the login page")
    public LoginPage open() {
        page.navigate(config().baseUrl());
        attachScreenshotOnReport("After navigating to the login page");

        return this;
    }

    @Step("Type <username> into 'Username' textbox")
    public LoginPage typeUsername(String username) {
        page.fill("id=user-name", username);
        attachScreenshotOnReport("After typing the username");

        return this;
    }

    @Step("Type <password> into 'Password' textbox")
    public LoginPage typePassword(String password) {
        page.fill("id=password", password);
        attachScreenshotOnReport("After typing the password");

        return this;
    }

    public Locator getErrorMessage() {
        return page.locator(".error-message-container h3");
    }

    @Step("Click on the 'Login' button")
    public ProductsPage submitLogin() {
        page.click("id=login-button");
        attachScreenshotOnReport("After clicking on the login button");

        return BasePageFactory.createInstance(page, ProductsPage.class);
    }

    @Step("Login attempt to Swag Labs")
    public ProductsPage loginAs(String username, String password) {
        open();
        typeUsername(username);
        typePassword(password);

        return submitLogin();
    }
}
