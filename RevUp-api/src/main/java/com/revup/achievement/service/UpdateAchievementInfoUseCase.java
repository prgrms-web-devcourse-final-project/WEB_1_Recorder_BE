package com.revup.achievement.service;

import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.model.mapper.AchievementMapper;
import com.revup.achievement.model.request.UpdateAchievementupdateVisibilityRequest;
import com.revup.achievement.model.response.UserAchievedInfoMapResponse;
import com.revup.annotation.UseCase;
import com.revup.common.BooleanStatus;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class UpdateAchievementInfoUseCase {

    private final UserAchievementService userAchievementService;
    private final AchievementMapper achievementMapper;

    public UserAchievedInfoMapResponse executeUpdateVisibility(
            UpdateAchievementupdateVisibilityRequest request,
            User user
    ) {
        List<UserAchievedInfo> info = userAchievementService.updateStatus(
                user,
                request.id(),
                BooleanStatus.from(request.status())
        );
        return achievementMapper.toUserAchievedInfoMapResponse(info);
    }
}
