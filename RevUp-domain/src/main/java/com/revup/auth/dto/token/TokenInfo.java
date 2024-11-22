package com.revup.auth.dto.token;

import com.revup.user.entity.LoginType;

public record TokenInfo(
        String socialId,
        LoginType loginType
) {

    public static TokenInfo of(String socialId, LoginType loginType) {
        return new TokenInfo(socialId, loginType);
    }
}
