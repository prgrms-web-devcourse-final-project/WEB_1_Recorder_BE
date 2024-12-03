package com.revup.user.controller;


import com.revup.annotation.SecurityUser;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import com.revup.user.model.request.UpdateEmailRequest;
import com.revup.user.model.request.UpdateProfileRequest;
import com.revup.user.model.request.ValidateEmailRequest;
import com.revup.user.model.response.UpdateAffiliationResponse;
import com.revup.user.model.response.UpdateProfileResponse;
import com.revup.user.model.response.ValidateEmailResponse;
import com.revup.user.service.UpdateUserUseCase;
import com.revup.user.service.ValidateEmailUseCase;
import com.revup.user.util.UserDomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UpdateUserUseCase updateUserUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;
    private final UserDomainUtil userDomainUtil;

    /**
     *  프로필 사진, 닉네임 한줄 소개 변경
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UpdateProfileResponse>> updateProfile(
            @RequestBody UpdateProfileRequest request,
            @SecurityUser User user
    ) {
        UpdateProfileResponse response = updateUserUseCase.executeUpdateProfile(user, request);
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
    public ResponseEntity<ApiResponse<UpdateAffiliationResponse>> updateEmail(
            @RequestBody UpdateEmailRequest request,
            @SecurityUser User user
    ) {
        UpdateAffiliationResponse response = updateUserUseCase.executeUpdateEmail(user, request);
        return ResponseEntity.ok()
                .body(ApiResponse.success(response));
    }
}
