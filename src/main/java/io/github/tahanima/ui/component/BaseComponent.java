package io.github.tahanima.ui.component;

import com.microsoft.playwright.Page;

/**
 * @author tahanima
 */
public abstract class BaseComponent {

    protected Page page;

    protected BaseComponent(Page page) {
        this.page = page;
    }
}
