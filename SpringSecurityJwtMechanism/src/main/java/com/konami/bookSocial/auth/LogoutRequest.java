package com.konami.bookSocial.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
    @NotNull(message = "Token is required")
    private String token;

}