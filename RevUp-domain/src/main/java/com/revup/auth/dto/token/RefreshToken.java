package com.revup.auth.dto.token;


import java.io.Serializable;

public record RefreshToken(
        String value,
        Long userId
) implements Serializable {
    public static RefreshToken of(String value) {
        return new RefreshToken(value, null);
    }
}
