package com.revup.achievement.service;

import com.revup.achieve.AchieveType;
import com.revup.achieve.dto.AchievementEventInfo;
import com.revup.achievement.entity.UserAchievement;
import com.revup.user.entity.User;

import java.util.List;

public interface AchievementService {

    List<UserAchievement> checkAndUpdateAchievement(AchievementEventInfo info);
}
