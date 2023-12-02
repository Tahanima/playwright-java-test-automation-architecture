package io.github.tahanima.util;

import static io.github.tahanima.config.ConfigurationManager.config;

import io.github.tahanima.annotation.DataSource;
import io.github.tahanima.dto.BaseDto;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

/**
 * @author tahanima
 */
public class DataArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<DataSource> {

    private String id;
    private String fileName;
    private Class<? extends BaseDto> clazz;

    @Override
    public void accept(final DataSource source) {
        id = source.id();
        fileName = config().baseTestDataPath() + source.fileName();
        clazz = source.clazz();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return Stream.of(CsvToDtoMapper.map(clazz, fileName, id)).map(Arguments::of);
    }
}
