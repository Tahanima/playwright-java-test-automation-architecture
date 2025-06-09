package io.github.tahanima.report;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static io.github.tahanima.config.ConfigurationManager.config;

/**
 * @author tahanima
 */
public final class AllureManager {

    private AllureManager() {}

    public static void setAllureEnvironmentInfo() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Platform", System.getProperty("os.name"))
                        .put("Version", System.getProperty("os.version"))
                        .put("Browser", StringUtils.capitalize(config().browser()))
                        .put("Context URL", config().baseUrl())
                        .build(),
                config().allureResultsDir() + "/");
    }
}
