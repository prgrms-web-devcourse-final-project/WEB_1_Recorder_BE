package com.revup.achievement.repository;

import com.revup.achievement.entity.Achievement;

import java.util.List;

public interface CustomAchievementRepository {

    List<Long> findAllIds();

    List<Achievement> findAllAchievements();
}
