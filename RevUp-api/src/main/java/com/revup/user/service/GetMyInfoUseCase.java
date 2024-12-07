package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.skillstack.model.mapper.SkillStackMapper;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;
import com.revup.user.model.mapper.UserMapper;
import com.revup.user.model.response.UserProfileResponse;
import com.revup.user.model.response.MySkillStacksResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetMyInfoUseCase {

    private final UserService userService;
    private final UserSkillStackService userSkillStackService;
    private final SkillStackMapper skillStackMapper;
    private final UserMapper userMapper;

    public UserProfileResponse executeGetMyProfile(User user) {
        return userMapper.toUserProfileResponse(user);
    }

    public MySkillStacksResponse executeGetMySkillStack(User user) {
        List<UserSkillStack> myStacks = userSkillStackService.findByUser(user);
        return skillStackMapper.toMySkillStacksResponse(myStacks);
    }


}
