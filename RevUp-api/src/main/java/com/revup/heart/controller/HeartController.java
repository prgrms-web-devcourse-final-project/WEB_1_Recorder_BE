package com.revup.heart.controller;

import com.revup.annotation.SecurityUser;
import com.revup.global.dto.ApiResponse;
import com.revup.heart.dto.request.HeartCreateRequest;
import com.revup.heart.dto.response.HeartIdResponse;
import com.revup.heart.usecase.CreateHeartUseCase;
import com.revup.heart.usecase.DeleteHeartUseCase;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequestMapping("/api/v1/heart")
@RequiredArgsConstructor
public class HeartController {
    private final CreateHeartUseCase createHeartUseCase;
    private final DeleteHeartUseCase deleteHeartUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<HeartIdResponse>> create(@RequestBody HeartCreateRequest request,
                                                               @SecurityUser User currentUser) {
        return success(HttpStatus.CREATED, createHeartUseCase.execute(request, currentUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id,
                                       @SecurityUser User currentUser) {
        deleteHeartUseCase.execute(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
