package io.github.tahanima.page;

import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

/**
 * @author tahanima
 */
public class BasePage {

    protected Page page;

    public void setPage(Page page) {
        this.page = page;
    }

    @Step
    public byte[] captureScreenshot() {
        return page.screenshot();
    }
}
