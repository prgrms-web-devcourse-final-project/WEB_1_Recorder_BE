package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.common.SkillStack;
import com.revup.common.skillstack.model.mapper.SkillStackMapper;
import com.revup.user.dto.Certification;
import com.revup.user.entity.Affiliation;
import com.revup.user.entity.Profile;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.request.MySkillStackId;
import com.revup.user.model.request.UpdateEmailRequest;
import com.revup.user.model.request.UpdateProfileRequest;
import com.revup.user.model.response.MySkillStacksResponse;
import com.revup.user.model.response.UpdateAffiliationResponse;
import com.revup.user.model.response.UpdateProfileResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserSkillStackService userSkillStackService;
    private final SkillStackMapper skillStackMapper;

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

    public MySkillStacksResponse executeUpdateSkillStack(User user, SkillStack skillStack) {
        List<UserSkillStack> updatedMyStacks = userSkillStackService.updateSkillStack(user, skillStack);
        return skillStackMapper.toMySkillStacksResponse(updatedMyStacks);
    }

    public MySkillStacksResponse executeDeleteMySkillStack(User user, MySkillStackId mySkillStackId) {
        List<UserSkillStack> updatedMyStacks = userSkillStackService.deleteMySkillStack(user ,mySkillStackId.id());
        return skillStackMapper.toMySkillStacksResponse(updatedMyStacks);
    }
}
