package io.github.tahanima.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

/**
 * @author tahanima
 */
@LoadPolicy(Config.LoadType.MERGE)
@Sources({"system:properties", "classpath:general.properties", "classpath:allure.properties"})
public interface Configuration extends Config {

    String browser();

    @Key("base.url")
    String baseUrl();

    boolean headless();

    @Key("slow.motion")
    int slowMotion();

    int timeout();

    @Key("base.test.data.path")
    String baseTestDataPath();

    @Key("allure.results.directory")
    String allureResultsDir();
}
