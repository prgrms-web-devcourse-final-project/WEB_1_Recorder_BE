package com.revup.achievement.model.response;

import com.revup.achieve.AchieveType;

import java.math.BigDecimal;
import java.util.List;

public record UserAchievementInfoResponse(
        List<Integer> conditions,
        Number current
) {
    public static UserAchievementInfoResponse of(
            List<Integer> conditions,
            BigDecimal current,
            AchieveType type
    ) {
        if(type.equals(AchieveType.ADOPTION_RATE)) {
            return new UserAchievementInfoResponse(conditions, current.floatValue());
        }

        return new UserAchievementInfoResponse(conditions, current.longValue());
    }
}
