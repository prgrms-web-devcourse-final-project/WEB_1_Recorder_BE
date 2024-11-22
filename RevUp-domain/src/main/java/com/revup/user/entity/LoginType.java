package com.revup.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    GITHUB("github"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String type;

    public static LoginType findByType(String loginType) {
        return Arrays.stream(values())
                .filter(value -> value.getType().equals(loginType))
                .findFirst()
                .orElse(null);
    }
}
