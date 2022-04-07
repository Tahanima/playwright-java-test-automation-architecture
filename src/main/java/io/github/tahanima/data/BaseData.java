package io.github.tahanima.data;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;

/**
 * @author tahanima
 */
public class BaseData {
    @Getter
    @Parsed(field = "Test Case ID", defaultNullRead = "")
    private String testCaseId;

    @Getter
    @Parsed(field = "Test Case Description", defaultNullRead = "")
    private String testCaseDescription;

    @Override
    public String toString() {
        return String.format(
                "testCaseId=%s, testCaseDescription=%s",
                testCaseId,
                testCaseDescription
        );
    }
}
