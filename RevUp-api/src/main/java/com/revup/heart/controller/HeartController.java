package com.revup.heart.controller;

import com.revup.global.dto.ApiResponse;
import com.revup.heart.dto.request.HeartCreateRequest;
import com.revup.heart.dto.response.HeartIdResponse;
import com.revup.heart.usecase.CreateHeartUseCase;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequestMapping("/api/v1/heart")
@RequiredArgsConstructor
public class HeartController {
    private final CreateHeartUseCase createHeartUseCase;
    private final UserUtil userUtil;

    @PostMapping
    public ResponseEntity<ApiResponse<HeartIdResponse>> insert(@RequestBody HeartCreateRequest request) {
        User currentUser = userUtil.getCurrentUser();
        return success(HttpStatus.CREATED, createHeartUseCase.execute(request, currentUser));
    }
}
