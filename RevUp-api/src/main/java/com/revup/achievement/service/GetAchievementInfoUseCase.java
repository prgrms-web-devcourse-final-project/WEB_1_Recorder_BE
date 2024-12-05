package com.revup.achievement.service;

import com.revup.achieve.AchieveType;
import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.dto.UserAchievementInfo;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.model.mapper.AchievementMapper;
import com.revup.achievement.model.response.SimpleUserAchievementInfoResponse;
import com.revup.achievement.model.response.UserAchievedInfoMapResponse;
import com.revup.achievement.model.response.UserAchievementDashboardResponse;
import com.revup.annotation.UseCase;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetAchievementInfoUseCase {

    private final UserAchievementService userAchievementService;
    private final AchievementMapper achievementMapper;

    public UserAchievementDashboardResponse executeGetUserAchievementDashboard(User user) {
        List<Achievement> achievements = userAchievementService.findAllAchievements();
        List<AchieveType> list = achievements.stream()
                .map(Achievement::getType)
                .distinct()
                .toList();
        List<UserAchievementInfo> infos = userAchievementService.findCurrentAchievements(user, list);

        return achievementMapper.toUserAchievementDashboardResponse(achievements, infos);
    }

    public SimpleUserAchievementInfoResponse executeGetUserAchievementSimpleInfo(User user) {
        int achievedCount = userAchievementService.findAchievedCount(user);
        int totalCount =  userAchievementService.findTotalAchievementsCount();

        return achievementMapper.toSimpleUserAchievementInfoResponse(totalCount, achievedCount);
    }

    public UserAchievedInfoMapResponse executeGetUserAchievedInfo(User user) {
        List<UserAchievedInfo> infos = userAchievementService.findUserAchievedInfo(user);
        return achievementMapper.toUserAchievedInfoMapResponse(infos);
    }
}
