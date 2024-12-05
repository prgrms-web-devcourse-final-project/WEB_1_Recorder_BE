package com.revup.achievement.model.request;

public record UpdateAchievementupdateVisibilityRequest(
        Long id,
        boolean status
) {
}
