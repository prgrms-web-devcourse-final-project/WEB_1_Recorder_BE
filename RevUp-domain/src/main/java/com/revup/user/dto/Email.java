package com.revup.user.dto;

public record Email(
        String value
) {
    public static Email of(String email) {
        return new Email(email);
    }
}
