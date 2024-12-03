package com.revup.achievement.service;

import com.revup.achieve.AchieveType;
import com.revup.achieve.dto.AchievementEventInfo;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.entity.UserAchievement;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementChecker achievementChecker;
    private final AchievementUpdater achievementUpdater;
    private final AchievementReader achievementReader;
    private final UserAdaptor userAdaptor;
    private static final int MAX_SIZE = 3;

    @Override
    @Transactional
    public List<UserAchievement> checkAndUpdateAchievement(final AchievementEventInfo info) {
        User user = userAdaptor.findById(info.userId());

        AchieveType type = info.type();
        List<UserAchievement> logs = achievementReader.findAchievementLog(user, type);
        if(logs.size() == MAX_SIZE) return List.of();    //업적을 모무 달성한 것이므로 달설할 없적이 없음.

        List<Achievement> achievements = achievementReader.findByType(type);

        List<UserAchievement> newLogs = switch (type) {
            case ADOPTION_COUNT -> achievementChecker.handleAdoption(user, achievements);
            case CUMULATIVE_LIKES -> achievementChecker.handleLikes(user, achievements);
            case REQUESTED_FEEDBACK -> achievementChecker.handleFeedback(user, achievements);
            case CONTINUOUS_ANSWER -> achievementChecker.handleAnswers(user, achievements);
            case ADOPTION_RATE -> achievementChecker.handleRate(user, achievements);
        };

        // 기존에 존재하는 업적을 제외하고 새로운 등록하려는 업적만 필터링
        List<UserAchievement> uniqueNewLogs = newLogs.stream()
                .filter(newLog -> logs.stream()
                        .noneMatch(log -> Objects.equals(newLog.getAchievement().getId(), log.getAchievement().getId()))
                )
                .toList();
        return uniqueNewLogs.isEmpty() ? List.of() : achievementUpdater.updateAll(uniqueNewLogs);

    }
}
