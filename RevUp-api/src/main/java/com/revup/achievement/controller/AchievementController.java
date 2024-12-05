package com.revup.achievement.controller;

import com.revup.achievement.model.request.UpdateAchievementupdateVisibilityRequest;
import com.revup.achievement.model.response.SimpleUserAchievementInfoResponse;
import com.revup.achievement.model.response.UserAchievedInfoMapResponse;
import com.revup.achievement.model.response.UserAchievementDashboardResponse;
import com.revup.achievement.service.GetAchievementInfoUseCase;
import com.revup.achievement.service.UpdateAchievementInfoUseCase;
import com.revup.annotation.SecurityUser;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.util.ResponseUtil.success;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievement")
public class AchievementController {

    private final GetAchievementInfoUseCase getAchievementInfoUseCase;
    private final UpdateAchievementInfoUseCase updateAchievementInfoUseCase;

    /**
     * 사용자 업적 현황 상세 ver
     * @param user
     * @return
     */
    @GetMapping("/user/info")
    public ResponseEntity<ApiResponse<UserAchievementDashboardResponse>> getUserAchievementDashboard(
            @SecurityUser User user
    ) {
        UserAchievementDashboardResponse response =
                getAchievementInfoUseCase.executeGetUserAchievementDashboard(user);
        return success(response);
    }

    /**
     * 사용자 업적 현황 간소화 ver
     * (전체 개수, 달성한 업적 개수)
     * @param user
     * @return
     */
    @GetMapping("/user/simple-info")
    public ResponseEntity<ApiResponse<SimpleUserAchievementInfoResponse>> getUserAchievementSimpleInfo(
            @SecurityUser User user
    ) {
        SimpleUserAchievementInfoResponse response =
                getAchievementInfoUseCase.executeGetUserAchievementSimpleInfo(user);
        return success(response);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserAchievedInfoMapResponse>> getUserAchievedInfo(
            @SecurityUser User user
    ) {
        UserAchievedInfoMapResponse response = getAchievementInfoUseCase.executeGetUserAchievedInfo(user);
        return success(response);
    }

    @PatchMapping("/user")
    public ResponseEntity<ApiResponse<UserAchievedInfoMapResponse>> updateVisibility(
            @RequestBody UpdateAchievementupdateVisibilityRequest request,
            @SecurityUser User user
    ) {
        UserAchievedInfoMapResponse response = updateAchievementInfoUseCase.executeUpdateVisibility(request, user);
        return success(response);
    }

}
