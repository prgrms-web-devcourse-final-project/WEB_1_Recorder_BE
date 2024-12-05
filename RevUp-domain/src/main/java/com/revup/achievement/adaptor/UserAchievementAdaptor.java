package com.revup.achievement.adaptor;

import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.repository.UserAchievementRepository;
import com.revup.annotation.Adaptor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class UserAchievementAdaptor {

    private final UserAchievementRepository userAchievementRepository;

    public List<Long> findAllByUserId(Long userId) {
        return userAchievementRepository.findAllIdsByUserId(userId);
    }

    public List<UserAchievedInfo> findAllInfoByUserId(Long userId) {
        return userAchievementRepository.findAllInfoByUserId(userId);
    }
}
