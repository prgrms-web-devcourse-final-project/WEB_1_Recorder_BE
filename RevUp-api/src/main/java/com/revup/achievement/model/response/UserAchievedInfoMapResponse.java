package com.revup.achievement.model.response;

import com.revup.achieve.AchieveType;
import com.revup.achievement.dto.UserAchievedInfo;

import java.util.*;


public record UserAchievedInfoMapResponse(
        Map<String, List<UserAchievedInfoResponse>> achievements
) {
    public static UserAchievedInfoMapResponse of(List<UserAchievedInfo> infos) {
        Map<String, List<UserAchievedInfoResponse>> map = new HashMap<>();

        for (UserAchievedInfo info : infos) {
            AchieveType type = info.achievement().getType();
            UserAchievedInfoResponse userAchievedInfoResponse = UserAchievedInfoResponse.of(info);
            map.computeIfAbsent(type.getType(), k -> new ArrayList<>()).add(userAchievedInfoResponse);
        }

        return new UserAchievedInfoMapResponse(map);
    }
}
