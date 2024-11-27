package com.revup.auth.dto.token;

import com.revup.user.entity.LoginType;

public record TokenInfo(
        Long id,
        String socialId,
        LoginType loginType
) {

    public static TokenInfo of(Long id, String socialId, LoginType loginType) {
        return new TokenInfo(id, socialId, loginType);
    }
}
