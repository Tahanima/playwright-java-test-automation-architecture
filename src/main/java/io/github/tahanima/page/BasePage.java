package io.github.tahanima.page;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public class BasePage {

    protected Page page;

    public void setAndConfigurePage(Page page) {
        this.page = page;
        page.setDefaultTimeout(config().timeout());
    }

    @Step
    public byte[] captureScreenshot() {
        return page.screenshot();
    }
}
