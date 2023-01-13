package io.github.tahanima.page.login;

import com.microsoft.playwright.Locator;
import io.github.tahanima.page.BasePage;

import static io.github.tahanima.config.ConfigurationManager.configuration;

/**
 * This class captures the relevant UI components and functionalities of the login page.
 *
 * @author tahanima
 */
public class LoginPage extends BasePage {
    public LoginPage goTo() {
        page.navigate(configuration().baseUrl());

        return this;
    }

    public LoginPage enterUsername(final String username) {
        page.fill("id=user-name", username);

        return this;
    }

    public LoginPage enterPassword(final String password) {
        page.fill("id=password", password);

        return this;
    }

    public Locator getErrorMessageLocator() {
        return page.locator(".error-message-container h3");
    }

    public void clickLogin() {
        page.click("id=login-button");
    }
}
