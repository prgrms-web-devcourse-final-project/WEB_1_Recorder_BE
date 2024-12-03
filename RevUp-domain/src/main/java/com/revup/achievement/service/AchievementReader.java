package com.revup.achievement.service;

import com.revup.achieve.AchieveType;
import com.revup.achievement.adaptor.AchievementAdaptor;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.entity.UserAchievement;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AchievementReader {

    private final AchievementAdaptor achievementAdaptor;

    public List<UserAchievement> findAchievementLog(User user, AchieveType type) {
        return achievementAdaptor.findAchievementLog(user, type);
    }

    public List<Achievement> findByType(AchieveType type) {
        return achievementAdaptor.findByType(type);
    }
}
