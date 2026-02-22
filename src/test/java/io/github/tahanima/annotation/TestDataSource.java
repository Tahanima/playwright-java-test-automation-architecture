package io.github.tahanima.annotation;

import io.github.tahanima.testdata.BaseTestData;
import io.github.tahanima.util.TestDataArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * @author tahanima
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(TestDataArgumentsProvider.class)
public @interface TestDataSource {

    String id();

    String fileName();

    Class<? extends BaseTestData> clazz();
}
