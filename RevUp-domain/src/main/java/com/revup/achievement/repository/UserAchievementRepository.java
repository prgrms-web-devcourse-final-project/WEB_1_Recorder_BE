package com.revup.achievement.repository;

import com.revup.achieve.AchieveType;
import com.revup.achievement.entity.UserAchievement;
import com.revup.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAchievementRepository extends CrudRepository<UserAchievement, Long> {

    List<UserAchievement> findAllByUserAndAchievement_Type(User user, AchieveType type);

}
