package com.revup.achievement.model.mapper;

import com.revup.achieve.AchieveType;
import com.revup.achievement.dto.UserAchievedInfo;
import com.revup.achievement.dto.UserAchievementInfo;
import com.revup.achievement.entity.Achievement;
import com.revup.achievement.model.response.SimpleUserAchievementInfoResponse;
import com.revup.achievement.model.response.UserAchievedInfoMapResponse;
import com.revup.achievement.model.response.UserAchievedInfoResponse;
import com.revup.achievement.model.response.UserAchievementDashboardResponse;
import com.revup.annotation.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public class AchievementMapper {
    public SimpleUserAchievementInfoResponse toSimpleUserAchievementInfoResponse(int totalCount, int achievedCount) {
        return SimpleUserAchievementInfoResponse.of(totalCount, achievedCount);
    }

    public UserAchievedInfoMapResponse toUserAchievedInfoMapResponse(List<UserAchievedInfo> info) {
        return UserAchievedInfoMapResponse.of(info);
    }

    public UserAchievementDashboardResponse toUserAchievementDashboardResponse(List<Achievement> achievements,
                                                                               List<UserAchievementInfo> infos) {
        // AchieveType별로 Achievement의 조건을 그룹화
        Map<AchieveType, List<Integer>> conditionsByType = achievements.stream()
                .collect(Collectors.groupingBy(
                        Achievement::getType,
                        Collectors.mapping(Achievement::getStandard, Collectors.toList())
                ));

        // AchieveType별로 UserAchievementInfo의 현재 값을 그룹화
        Map<AchieveType, BigDecimal> currentByType = infos.stream()
                .collect(Collectors.toMap(
                        UserAchievementInfo::type,
                        UserAchievementInfo::current,
                        (existing, replacement) -> existing // 중복 키 처리
                ));

        return UserAchievementDashboardResponse.of(conditionsByType, currentByType);
    }

    public UserAchievedInfoResponse toUserAchievedInfoResponse(UserAchievedInfo info) {
        return UserAchievedInfoResponse.of(info);
    }
}
