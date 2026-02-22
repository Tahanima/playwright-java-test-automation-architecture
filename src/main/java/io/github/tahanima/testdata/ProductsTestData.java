package io.github.tahanima.testdata;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.ToString;

/**
 * @author tahanima
 */
@Getter
@ToString(callSuper = true)
public final class ProductsTestData extends BaseTestData {

    @Parsed(field = "Username", defaultNullRead = "")
    private String username;

    @Parsed(field = "Password", defaultNullRead = "")
    private String password;

    @Parsed(field = "URL", defaultNullRead = "")
    private String url;
}
