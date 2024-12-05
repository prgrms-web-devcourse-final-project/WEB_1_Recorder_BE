package com.revup.achievement.model.response;

public record SimpleUserAchievementInfoResponse(
        int total,
        int achieved
) {
    public static SimpleUserAchievementInfoResponse of(int total, int achieved) {
        return new SimpleUserAchievementInfoResponse(total, achieved);
    }
}
