package io.github.tahanima.util;

import io.github.tahanima.data.BaseTestData;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * @author tahanima
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest
@ArgumentsSource(DataArgumentsProvider.class)
public @interface DataSource {

    String testCaseId();

    String filePath();

    Class<? extends BaseTestData> clazz();
}
