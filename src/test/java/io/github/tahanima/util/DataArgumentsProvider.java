package io.github.tahanima.util;

import static io.github.tahanima.config.ConfigurationManager.config;

import io.github.tahanima.data.BaseTestData;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

/**
 * @author tahanima
 */
public class DataArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<DataSource> {

    private String testCaseId;
    private String filePath;
    private Class<? extends BaseTestData> clazz;

    @Override
    public void accept(DataSource source) {
        testCaseId = source.testCaseId();
        filePath = config().baseTestDataPath() + source.filePath();
        clazz = source.clazz();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(CsvToPOJOMapper.map(clazz, filePath, testCaseId))
                .map(Arguments::of);
    }
}
