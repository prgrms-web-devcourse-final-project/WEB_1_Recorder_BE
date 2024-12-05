package com.revup.achievement.model.response;

import com.revup.achieve.AchieveType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record UserAchievementDashboardResponse(
        Map<String, UserAchievementInfoResponse> dashboard
) {

    public static UserAchievementDashboardResponse of(
            Map<AchieveType, List<Integer>> conditionsByType,
            Map<AchieveType, BigDecimal> currentByType
    ) {
        Map<String, UserAchievementInfoResponse> map = new HashMap<>();
        for (AchieveType type : AchieveType.values()) {
            List<Integer> conditions = conditionsByType.getOrDefault(type, List.of());
            BigDecimal current = currentByType.getOrDefault(type, BigDecimal.ZERO);
            UserAchievementInfoResponse userAchievementInfo =
                    UserAchievementInfoResponse.of(conditions, current, type);

            map.put(type.getType(), userAchievementInfo);
        }

        return new UserAchievementDashboardResponse(map);
    }
}
