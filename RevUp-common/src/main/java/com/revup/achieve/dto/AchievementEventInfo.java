package com.revup.achieve.dto;

import com.revup.achieve.AchieveType;

public record AchievementEventInfo(
        Long userId,
        AchieveType type
) {
}
