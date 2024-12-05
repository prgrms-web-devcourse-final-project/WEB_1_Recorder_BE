package com.revup.achievement.service;

import com.revup.achievement.entity.Achievement;
import com.revup.achievement.entity.UserAchievement;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementCreator {

    private final AchievementProgressCalculator achievementProgressCalculator;

    //채택 답변수
    public List<UserAchievement> handleAdoption(User user, List<Achievement> achievements) {
        long acceptionCount = achievementProgressCalculator.calculateAdoption(user).longValue();
        return getNewUserAchievements(user, achievements, acceptionCount);
    }

    //누적 좋아요
    public List<UserAchievement> handleLikes(User user, List<Achievement> achievements) {
        long totalGoodCount = achievementProgressCalculator.calculateLikes(user).longValue();
        return getNewUserAchievements(user, achievements, totalGoodCount);
    }

    //받은 피드백 요청 수
    public List<UserAchievement> handleFeedback(User user, List<Achievement> achievements) {
        long total = achievementProgressCalculator.calculateRequestedFeedbackCount(user).longValue();

        return getNewUserAchievements(user, achievements, total);
    }

    //연속 답변일수
    public List<UserAchievement> handleAnswers(User user, List<Achievement> achievements) {
        int maxDate = achievementProgressCalculator.calculateAnswerDate(user).intValue();
        return getNewUserAchievements(user, achievements, maxDate);
    }

    //채택률
    public List<UserAchievement> handleRate(User user, List<Achievement> achievements) {
        BigDecimal percentage = achievementProgressCalculator.calculateAdoptionRate(user);

        return achievements.stream()
                .filter(achievement -> BigDecimal.valueOf(achievement.getStandard()).compareTo(percentage) <= 0)
                .map(achievement -> UserAchievement.of(user, achievement))
                .toList();
    }

    private static List<UserAchievement> getNewUserAchievements(User user, List<Achievement> achievements, long value) {
        return achievements.stream()
                .filter(achievement -> achievement.getStandard() <= value)
                .map(achievement -> UserAchievement.of(user, achievement))
                .toList();
    }
}
