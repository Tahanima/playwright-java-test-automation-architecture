package io.github.tahanima.util;

import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;

import io.github.tahanima.testdata.BaseTestData;

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
public final class CsvLoader {

    private CsvLoader() {}

    private static Object[] readAndProcess(
            Class<? extends BaseTestData> clazz, String file, String id) {
        CsvParserSettings settings = new CsvParserSettings();

        settings.getFormat().setLineSeparator("\n");
        settings.trimValues(true);

        CsvRoutines routines = new CsvRoutines(settings);

        try (var reader =
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            var testData = new ArrayList<BaseTestData>();

            routines.iterate(clazz, reader)
                    .forEach(
                            e -> {
                                if (e.getTestCaseId().equals(id)) {
                                    testData.add(e);
                                }
                            });

            return testData.toArray();
        } catch (IOException e) {
            log.error("CsvLoader::readAndProcess", e);
        }

        throw new NullPointerException("Couldn't provide test data");
    }

    public static Object[] load(Class<? extends BaseTestData> clazz, String file, String id) {
        return readAndProcess(clazz, file, id);
    }
}
