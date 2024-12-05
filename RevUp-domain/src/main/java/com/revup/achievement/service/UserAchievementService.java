package com.revup.achievement.service;

import com.revup.achieve.AchieveType;
import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.dto.UserAchievementInfo;
import com.revup.achievement.entity.Achievement;

import com.revup.common.BooleanStatus;
import com.revup.user.entity.User;

import java.util.List;

public interface UserAchievementService {

    int findAchievedCount(User user);

    int findTotalAchievementsCount();

    List<UserAchievedInfo> findUserAchievedInfo(User user);

    List<Achievement> findAllAchievements();

    List<UserAchievementInfo> findCurrentAchievements(User user, List<AchieveType> list);

    List<UserAchievedInfo> updateStatus(User user, Long userAchievementId, BooleanStatus status);
}
