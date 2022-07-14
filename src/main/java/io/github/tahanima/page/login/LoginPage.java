package io.github.tahanima.page.login;

import io.github.tahanima.page.BasePage;

import static io.github.tahanima.config.ConfigurationManager.configuration;

/**
 * @author tahanima
 */
public class LoginPage extends BasePage {
  public LoginPage goTo() {
    page.navigate(configuration().baseUrl());

    return this;
  }

  public LoginPage enterUsername(final String username) {
    page.fill("id=user-name", "");
    page.fill("id=user-name", username);

    return this;
  }

  public LoginPage enterPassword(final String password) {
    page.fill("id=password", "");
    page.fill("id=password", password);

    return this;
  }

  public String getErrorMessage() {
    return page.locator(".error-message-container h3").textContent();
  }

  public void clickLogin() {
    page.click("id=login-button");
  }
}
