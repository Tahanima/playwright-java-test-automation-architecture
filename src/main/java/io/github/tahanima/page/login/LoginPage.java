package io.github.tahanima.page.login;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Locator;

import io.github.tahanima.page.BasePage;

/**
 * This class captures the relevant UI components and functionalities of the login page.
 *
 * @author tahanima
 */
public class LoginPage extends BasePage {
    public LoginPage navigateToUrl() {
        page.navigate(config().baseUrl());

        return this;
    }

    public LoginPage fillUsernameInTextBox(String username) {
        page.fill("id=user-name", username);

        return this;
    }

    public LoginPage fillPasswordInTextBox(String password) {
        page.fill("id=password", password);

        return this;
    }

    public Locator getErrorMessage() {
        return page.locator(".error-message-container h3");
    }

    public void clickOnLoginButton() {
        page.click("id=login-button");
    }
}
