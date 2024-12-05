package com.revup.achievement.dto;

import com.revup.achievement.entity.Achievement;
import com.revup.common.BooleanStatus;

public record UserAchievedInfo(
        Long id,
        Achievement achievement,
        int step,
        BooleanStatus isVisible
) {
}
