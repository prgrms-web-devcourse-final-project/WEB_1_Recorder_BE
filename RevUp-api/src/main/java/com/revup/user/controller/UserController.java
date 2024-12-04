package com.revup.user.controller;


import com.revup.annotation.SecurityUser;
import com.revup.global.dto.ApiResponse;
import com.revup.user.entity.User;
import com.revup.user.model.request.*;
import com.revup.user.model.response.*;
import com.revup.user.service.GetMyInfoUseCase;
import com.revup.user.service.UpdateUserUseCase;
import com.revup.user.service.ValidateEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.revup.global.util.ResponseUtil.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UpdateUserUseCase updateUserUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;
    private final GetMyInfoUseCase getMyInfoUseCase;

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
        return success(response);
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
        return success(HttpStatus.CREATED, response);
    }

    /**
     * 이메일 수정
     * @param request
     * @param user
     * @return
     */
    @PatchMapping("/email")
    public ResponseEntity<ApiResponse<UpdateAffiliationResponse>> updateEmail(
            @RequestBody UpdateEmailRequest request,
            @SecurityUser User user
    ) {
        UpdateAffiliationResponse response = updateUserUseCase.executeUpdateEmail(user, request);
        return success(response);
    }

    /**
     * 나의 기술 스택 정보 조회
     * @param user
     * @return
     */
    @GetMapping("/tech")
    public ResponseEntity<ApiResponse<MySkillStacksResponse>> getMySkillStack(
            @SecurityUser User user
    ) {
        MySkillStacksResponse response = getMyInfoUseCase.executeGetMySkillStack(user);
        return success(response);
    }

    @PostMapping("/tech")
    public ResponseEntity<ApiResponse<MySkillStacksResponse>> updateMySkillStack(
            @SecurityUser User user,
            @RequestBody UpdateMySkillStackRequest stack
    ) {
        MySkillStacksResponse response = updateUserUseCase.executeUpdateSkillStack(user, stack.toSkillStack());
        return success(response);
    }

    @DeleteMapping("/tech")
    public ResponseEntity<ApiResponse<MySkillStacksResponse>> deleteMySkillStack(
            @RequestBody MySkillStackId id,
            @SecurityUser User user
    ) {
        MySkillStacksResponse response = updateUserUseCase.executeDeleteMySkillStack(user, id);
        return success(response);
    }
}
