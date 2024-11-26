package com.revup.user.controller;


import com.revup.global.dto.ApiResponse;
import com.revup.user.model.request.UpdateEmailRequest;
import com.revup.user.model.request.UpdateProfileRequest;
import com.revup.user.model.request.ValidateEmailRequest;
import com.revup.user.model.response.UpdateEmailResponse;
import com.revup.user.model.response.UpdateProfileResponse;
import com.revup.user.model.response.ValidateEmailResponse;
import com.revup.user.service.UpdateEmailUseCase;
import com.revup.user.service.UpdateProfileUseCase;
import com.revup.user.service.ValidateEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UpdateProfileUseCase updateProfileUseCase;
    private final UpdateEmailUseCase updateEmailUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;

    /**
     *  프로필 사진, 닉네임 한줄 소개 변경
     * @param request
     * @return
     */
    @PatchMapping
    public ResponseEntity<ApiResponse<UpdateProfileResponse>> updateProfile(
            @RequestBody UpdateProfileRequest request
    ) {
        UpdateProfileResponse response = updateProfileUseCase.execute(request);
        return ResponseEntity.ok().body(ApiResponse.success(response));
    }

    /**
     * 이메일 인증 요청
     * @param request
     * @return
     */
    @PostMapping("/email")
    public ResponseEntity<ApiResponse<ValidateEmailResponse>> validateEmail(
            @RequestBody ValidateEmailRequest request
    ) {
        ValidateEmailResponse response = validateEmailUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PatchMapping("/email")
    public ResponseEntity<ApiResponse<UpdateEmailResponse>> updateEmail(
            @RequestBody UpdateEmailRequest request
    ) {
        UpdateEmailResponse response = updateEmailUseCase.execute(request);
        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }
}
