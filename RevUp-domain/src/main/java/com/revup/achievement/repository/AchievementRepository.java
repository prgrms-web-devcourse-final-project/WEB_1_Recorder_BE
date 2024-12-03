package com.revup.achievement.repository;

import com.revup.achieve.AchieveType;
import com.revup.achievement.entity.Achievement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    List<Achievement> findByType(AchieveType type);

}
