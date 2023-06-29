package io.github.tahanima.data;

import com.univocity.parsers.annotations.Parsed;

import lombok.Getter;
import lombok.ToString;

/**
 * @author tahanima
 */
@Getter
@ToString
public class BaseData {

    @Parsed(field = "Test Case ID", defaultNullRead = "")
    private String testCaseId;
}
