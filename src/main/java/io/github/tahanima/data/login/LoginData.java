package io.github.tahanima.data.login;

import com.univocity.parsers.annotations.Parsed;
import io.github.tahanima.data.BaseData;
import lombok.Getter;

/**
 * @author tahanima
 */
public class LoginData extends BaseData {
    @Getter
    @Parsed(field = "User Name", defaultNullRead = "")
    private String userName;

    @Getter
    @Parsed(field = "Password", defaultNullRead = "")
    private String password;

    @Getter
    @Parsed(field = "Error Message", defaultNullRead = "")
    private String errorMessage;

    @Override
    public String toString() {
        return String.format(
                "{%s, userName=%s, password=%s, errorMessage=%s}",
                super.toString(),
                userName,
                password,
                errorMessage
        );
    }
}
