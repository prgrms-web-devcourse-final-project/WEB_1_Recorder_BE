package com.revup.auth.model.dto.request;

public record TokenRequest(
        String accessToken,
        String refreshToken
) {
}
