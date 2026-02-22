package io.github.tahanima.util;

import static io.github.tahanima.config.ConfigurationManager.config;

import io.github.tahanima.annotation.TestDataSource;
import io.github.tahanima.testdata.BaseTestData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

/**
 * @author tahanima
 */
public class TestDataArgumentsProvider
        implements ArgumentsProvider, AnnotationConsumer<TestDataSource> {

    private String id;
    private String fileName;
    private Class<? extends BaseTestData> clazz;

    @Override
    public void accept(TestDataSource source) {
        id = source.id();
        fileName = config().baseTestDataPath() + source.fileName();
        clazz = source.clazz();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(CsvLoader.load(clazz, fileName, id)).map(Arguments::of);
    }
}
