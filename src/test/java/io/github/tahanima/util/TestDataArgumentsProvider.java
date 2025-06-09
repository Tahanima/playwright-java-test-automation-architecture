package io.github.tahanima.util;

import io.github.tahanima.annotation.TestDataSource;
import io.github.tahanima.fixture.BaseFixture;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.github.tahanima.config.ConfigurationManager.config;

/**
 * @author tahanima
 */
public class TestDataArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<TestDataSource> {

    private String id;
    private String fileName;
    private Class<? extends BaseFixture> clazz;

    @Override
    public void accept(final TestDataSource source) {
        id = source.id();
        fileName = config().baseTestDataPath() + source.fileName();
        clazz = source.clazz();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Stream.of(TestFixtureCsvLoader.load(clazz, fileName, id)).map(Arguments::of);
    }
}
