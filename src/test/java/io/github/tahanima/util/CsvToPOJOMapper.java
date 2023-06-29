package io.github.tahanima.util;

import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvRoutines;

import io.github.tahanima.data.BaseData;

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
            final Class<? extends BaseData> clazz, final String fileName, final String id) {
        CsvParserSettings settings = new CsvParserSettings();

        settings.getFormat().setLineSeparator("\n");
        settings.trimValues(false);

        CsvRoutines routines = new CsvRoutines(settings);

        try (Reader reader =
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)) {
            ArrayList<BaseData> testData = new ArrayList<>();

            routines.iterate(clazz, reader)
                    .forEach(
                            e -> {
                                if (e.getTestCaseId().equals(id)) testData.add(e);
                            });

            return testData.toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Couldn't provide test data");
    }
}
