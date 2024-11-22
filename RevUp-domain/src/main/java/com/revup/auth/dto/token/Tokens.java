package com.revup.auth.dto.token;

import lombok.Builder;

@Builder
public record Tokens(
        AccessToken accessToken,
        RefreshToken refreshToken
) {

}
