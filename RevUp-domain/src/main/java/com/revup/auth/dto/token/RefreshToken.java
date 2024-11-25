package com.revup.auth.dto.token;


import java.io.Serializable;

public record RefreshToken(
        String value
) implements Serializable {
    public static RefreshToken of(String value) {
        return new RefreshToken(value);
    }
}
