package io.github.tahanima.page;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;

import static io.github.tahanima.config.ConfigurationManager.configuration;

/**
 * @author tahanima
 */
public class BasePage {
  protected Page page;

  public void initialize(final Page page) {
    this.page = page;
  }

  public void captureScreenshot(final String fileName) {
    page.screenshot(
        new Page.ScreenshotOptions()
            .setPath(
                Paths.get(
                    configuration().baseReportPath()
                        + configuration().baseScreenshotPath()
                        + "/"
                        + fileName
                        + ".png"))
            .setFullPage(true));
  }
}
