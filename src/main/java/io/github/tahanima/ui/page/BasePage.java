package io.github.tahanima.ui.page;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public abstract class BasePage {

    protected Page page;

    public void setAndConfigurePage(final Page page) {
        this.page = page;

        page.setDefaultTimeout(config().timeout());
    }

    public void initComponents() {}

    @Step
    public byte[] captureScreenshot() {
        return page.screenshot();
    }
}
