package com.revup.achievement.service;

import com.revup.achieve.AchieveType;
import com.revup.achievement.adaptor.AchievementAdaptor;
import com.revup.achievement.adaptor.UserAchievementAdaptor;
import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.dto.UserAchievementInfo;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.entity.UserAchievement;
import com.revup.common.BooleanStatus;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAchievementServiceImpl implements UserAchievementService {

    private final UserAchievementAdaptor userAchievementAdaptor;
    private final AchievementAdaptor achievementAdaptor;
    private final AchievementProgressCalculator achievementProgressCalculator;

    @Override
    public int findAchievedCount(User user) {
        List<Long> achievedIds = userAchievementAdaptor.findAllByUserId(user.getId());
        return achievedIds.size();
    }

    @Override
    public int findTotalAchievementsCount() {
        List<Long> totalAchievementIds = achievementAdaptor.findAllIds();
        return totalAchievementIds.size();
    }

    @Override
    public List<UserAchievedInfo> findUserAchievedInfo(User user) {
        return userAchievementAdaptor.findAllInfoByUserId(user.getId());
    }

    @Override
    public List<Achievement> findAllAchievements() {
        return achievementAdaptor.findAll();
    }

    @Override
    public List<UserAchievementInfo> findCurrentAchievements(final User user, List<AchieveType> list) {
        return list.stream()
                .map(type ->
                        UserAchievementInfo.of(
                                type,
                                getCurrentByType(user, type)
                        )
                )
                .toList();
    }

    private BigDecimal getCurrentByType(User user, AchieveType achieveType) {
        return switch (achieveType) {
            case ADOPTION_RATE -> achievementProgressCalculator.calculateAdoptionRate(user);
            case ADOPTION_COUNT -> achievementProgressCalculator.calculateAdoption(user);
            case CUMULATIVE_LIKES -> achievementProgressCalculator.calculateLikes(user);
            case CONTINUOUS_ANSWER -> achievementProgressCalculator.calculateAnswerDate(user);
            case REQUESTED_FEEDBACK -> achievementProgressCalculator.calculateRequestedFeedbackCount(user);
        };
    }

    @Override
    @Transactional
    public List<UserAchievedInfo> updateStatus(User user, Long userAchievementId, BooleanStatus status) {
        UserAchievement userAchievement = achievementAdaptor.findById(userAchievementId);
        userAchievement.updateVisibility(status);

        return userAchievementAdaptor.findAllInfoByUserId(user.getId());
    }

}
