package com.revup.achievement.dto;

import com.revup.achieve.AchieveType;

import java.math.BigDecimal;

public record UserAchievementInfo(
        AchieveType type,
        BigDecimal current
) {
    public static UserAchievementInfo of(AchieveType type, BigDecimal current) {
        return new UserAchievementInfo(type, current);
    }
}
