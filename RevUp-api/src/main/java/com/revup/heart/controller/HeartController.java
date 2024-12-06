package com.revup.heart.controller;

import com.revup.annotation.SecurityUser;
import com.revup.global.dto.ApiResponse;
import com.revup.heart.dto.request.HeartRequest;
import com.revup.heart.dto.response.HeartCountResponse;
import com.revup.heart.dto.response.HeartStateResponse;
import com.revup.heart.usecase.HeartUseCase;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequestMapping("/api/v1/answers/{answerId}/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartUseCase heartUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<HeartStateResponse>> process(@PathVariable Long answerId,
                                        @RequestBody HeartRequest request,
                                        @SecurityUser User currentUser) {

        return success(heartUseCase.process(answerId, request, currentUser.getId()));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<HeartStateResponse>> getState(@PathVariable Long answerId,
                                                                    @SecurityUser User currentUser){
        return success(heartUseCase.getState(answerId, currentUser));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<HeartCountResponse>> getCount(@PathVariable Long answerId){
        return success(heartUseCase.getCount(answerId));
    }

}
