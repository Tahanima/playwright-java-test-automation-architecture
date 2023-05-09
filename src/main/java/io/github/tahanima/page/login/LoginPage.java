package io.github.tahanima.page.login;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Locator;

import io.github.tahanima.page.BasePage;
import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public class LoginPage extends BasePage {

    @Step
    public LoginPage navigateToUrl() {
        page.navigate(config().baseUrl());

        return this;
    }

    @Step
    public LoginPage fillUsernameInTextBox(String username) {
        page.fill("id=user-name", username);

        return this;
    }

    @Step
    public LoginPage fillPasswordInTextBox(String password) {
        page.fill("id=password", password);

        return this;
    }

    @Step
    public Locator getErrorMessage() {
        return page.locator(".error-message-container h3");
    }

    @Step
    public void clickOnLoginButton() {
        page.click("id=login-button");
    }
}
