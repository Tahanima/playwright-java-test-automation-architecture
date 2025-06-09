package io.github.tahanima.util;

import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;
import io.github.tahanima.fixture.BaseFixture;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author tahanima
 */
@Slf4j
public final class TestFixtureCsvLoader {

    private TestFixtureCsvLoader() {}

    private static Object[] readAndProcess(
            final Class<? extends BaseFixture> clazz, final String file, final String id) {
        final CsvParserSettings settings = new CsvParserSettings();

        settings.getFormat().setLineSeparator("\n");
        settings.trimValues(true);

        final CsvRoutines routines = new CsvRoutines(settings);

        try (var reader =
                     new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            var fixtures = new ArrayList<BaseFixture>();

            routines.iterate(clazz, reader)
                    .forEach(
                            e -> {
                                if (e.getTestCaseId().equals(id)) {
                                    fixtures.add(e);
                                }
                            });

            return fixtures.toArray();
        } catch (IOException e) {
            log.error("TestFixtureCsvLoader::readAndProcess", e);
        }

        throw new NullPointerException("Couldn't provide test data");
    }

    public static Object[] load(
            final Class<? extends BaseFixture> clazz, final String file, final String id) {
        return readAndProcess(clazz, file, id);
    }
}
