package com.revup.achievement.model.response;

import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.entity.Achievement;

public record UserAchievedInfoResponse(
        Long id,
        String content,
        int step,
        boolean isVisible
) {
    public static UserAchievedInfoResponse of(UserAchievedInfo info) {
        Achievement achievement = info.achievement();
        String content = achievement.getType().getContent(achievement.getStandard());

        return new UserAchievedInfoResponse(info.id(), content, info.step(), info.isVisible().toBoolean());
    }
}
