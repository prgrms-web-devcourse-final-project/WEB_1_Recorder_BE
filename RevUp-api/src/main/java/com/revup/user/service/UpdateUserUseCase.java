package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.user.dto.Certification;
import com.revup.user.entity.Affiliation;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.request.UpdateEmailRequest;
import com.revup.user.model.request.UpdateProfileRequest;
import com.revup.user.model.response.UpdateAffiliationResponse;
import com.revup.user.model.response.UpdateProfileResponse;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserService userService;
    private final UserMapper userMapper;

    public UpdateProfileResponse executeUpdateProfile(User user, UpdateProfileRequest request) {
        Profile updatedProfile = userService.updateProfile(user, request.toProfile());
        UpdateProfileResponse updateProfileResponse = userMapper.toUpdateProfileResponse(updatedProfile);
        return request.includeResponse() ? updateProfileResponse : null;
    }

    public UpdateAffiliationResponse executeUpdateEmail(User user, UpdateEmailRequest request) {
        Certification certification = request.toCertification();
        Affiliation affiliation = userService.updateEmail(user, certification, request.toEmail());
        return userMapper.toUpdateEmailResponse(affiliation);
    }
}
