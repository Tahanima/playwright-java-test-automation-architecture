package io.github.tahanima.ui.page;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

import static io.github.tahanima.config.ConfigurationManager.config;

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

    public byte[] captureScreenshot() {
        return page.screenshot();
    }

    public void attachScreenshotOnReport(final String stepName) {
        Allure.attachment(stepName, new ByteArrayInputStream(captureScreenshot()));
    }
}
