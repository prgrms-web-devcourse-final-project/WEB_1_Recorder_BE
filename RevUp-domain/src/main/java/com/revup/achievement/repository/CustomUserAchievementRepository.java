package com.revup.achievement.repository;

import com.revup.achievement.dto.UserAchievedInfo;

import java.util.List;

public interface CustomUserAchievementRepository {

    List<Long> findAllIdsByUserId(Long userId);

    List<UserAchievedInfo> findAllInfoByUserId(Long userId);
}
