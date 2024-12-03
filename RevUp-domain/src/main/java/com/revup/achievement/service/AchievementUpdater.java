package com.revup.achievement.service;

import com.revup.achievement.adaptor.AchievementAdaptor;
import com.revup.achievement.entity.UserAchievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AchievementUpdater {

    private final AchievementAdaptor achievementAdaptor;
    public List<UserAchievement> updateAll(List<UserAchievement> newLogs) {
        return achievementAdaptor.saveUserAchievements(newLogs);
    }
}
