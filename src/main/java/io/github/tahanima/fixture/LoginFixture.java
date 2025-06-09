package io.github.tahanima.fixture;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.ToString;

/**
 * @author tahanima
 */
@Getter
@ToString(callSuper = true)
public class LoginFixture extends BaseFixture {

    @Parsed(field = "Username", defaultNullRead = "")
    private String username;

    @Parsed(field = "Password", defaultNullRead = "")
    private String password;

    @Parsed(field = "Error Message", defaultNullRead = "")
    private String errorMessage;
}
