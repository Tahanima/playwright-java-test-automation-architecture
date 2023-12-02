package io.github.tahanima.annotation;

import io.github.tahanima.dto.BaseDto;
import io.github.tahanima.util.DataArgumentsProvider;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * @author tahanima
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(DataArgumentsProvider.class)
public @interface DataSource {

    String id();

    String fileName();

    Class<? extends BaseDto> clazz();
}
