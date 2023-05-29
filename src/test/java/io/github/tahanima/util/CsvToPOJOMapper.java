package io.github.tahanima.util;

import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;

import io.github.tahanima.data.BaseTestData;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author tahanima
 */
public final class CsvToPOJOMapper {

    private CsvToPOJOMapper() {}

    public static Object[] map(
            Class<? extends BaseTestData> clazz, String csvFilePath, String testCaseId) {
        CsvParserSettings parserSettings = new CsvParserSettings();

        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.trimValues(false);

        CsvRoutines routines = new CsvRoutines(parserSettings);

        try (Reader inputReader =
                new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8)) {
            ArrayList<BaseTestData> testData = new ArrayList<>();

            routines.iterate(clazz, inputReader)
                    .forEach(
                            e -> {
                                if (e.getTestCaseId().equals(testCaseId)) testData.add(e);
                            });

            return testData.toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Couldn't provide test data");
    }
}
