package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.user.entity.User;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.request.UpdateProfileRequest;
import com.revup.user.model.response.UpdateProfileResponse;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateProfileUseCase {

    private final UserUpdater userUpdater;
    private final UserMapper userMapper;
    private final UserUtil userUtil;


    public UpdateProfileResponse execute(UpdateProfileRequest request) {
        User currentUser = userUtil.getCurrentUser();
        User updatedUser = userUpdater.updateProfile(currentUser, request.toProfile());
        UpdateProfileResponse updateProfileResponse = userMapper.toUpdateProfileResponse(updatedUser);
        return request.includeResponse() ? updateProfileResponse : null;
    }
}
