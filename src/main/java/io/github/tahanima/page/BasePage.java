package io.github.tahanima.page;

import static io.github.tahanima.config.ConfigurationManager.config;

import com.microsoft.playwright.Page;

import java.nio.file.Path;

/**
 * @author tahanima
 */
public class BasePage {
    protected Page page;

    public void setAndConfigurePage(Page page) {
        this.page = page;
        page.setDefaultTimeout(config().timeout());
    }

    private String getScreenshotFilePath(String path) {
        return config().baseScreenshotPath() + path;
    }

    public void captureScreenshot(String fileName) {
        page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(Path.of(String.format("%s.png", getScreenshotFilePath(fileName))))
                        .setFullPage(true));
    }
}
