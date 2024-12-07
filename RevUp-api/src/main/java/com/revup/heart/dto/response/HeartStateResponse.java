package com.revup.heart.dto.response;

import com.revup.heart.enums.HeartType;

public record HeartStateResponse(
        boolean isGood,
        boolean isBad
) {
    public static HeartStateResponse of (HeartType heartType){
        return new HeartStateResponse(
                heartType == HeartType.GOOD,
                heartType == HeartType.BAD
        );
    }
}
