package com.revup.heart.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HeartType {
    GOOD("good"),
    BAD("bad");

    private final String value;

    public static HeartType from(boolean isGood){
        return isGood ? GOOD : BAD;
    }
}
