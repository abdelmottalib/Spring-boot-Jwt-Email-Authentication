package com.konami.bookSocial.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessErrorCode {
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No code"),
    INCORRECT_PASSWORD(5, HttpStatus.BAD_REQUEST, "Incorrect password"),
    ACCOUNT_DISABLED(4, HttpStatus.FORBIDDEN, "Account disabled"),
    NEW_PASSWORD_DOESNT_MATCH(3, HttpStatus.BAD_REQUEST, "New password doesn't match"),
    INCORRECT_CREDENTIALS(2, HttpStatus.UNAUTHORIZED, "Incorrect credentials"),
    ACCOUNT_LOCKED(1, HttpStatus.LOCKED, "Account locked"),
    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
