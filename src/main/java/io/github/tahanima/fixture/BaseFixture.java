package io.github.tahanima.fixture;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.ToString;

/**
 * @author tahanima
 */
@Getter
@ToString
public class BaseFixture {

    @Parsed(field = "Test Case ID", defaultNullRead = "")
    private String testCaseId;
}
