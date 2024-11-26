package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.user.entity.User;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.request.UpdateProfileRequest;
import com.revup.user.model.response.UpdateProfileResponse;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateProfileUseCase {

    private final UserUpdater userUpdater;
    private final UserMapper userMapper;

    /**
     * 회원 정보 업데이트
     */
    public UpdateProfileResponse execute(UpdateProfileRequest request) {
        User updatedUser = userUpdater.updateProfile(request.toProfile());
        UpdateProfileResponse updateProfileResponse = userMapper.toUpdateProfileResponse(updatedUser);
        return request.includeResponse() ? updateProfileResponse : null;
    }
}
