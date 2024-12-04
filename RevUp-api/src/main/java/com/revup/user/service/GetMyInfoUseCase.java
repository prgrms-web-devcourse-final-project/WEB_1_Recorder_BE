package com.revup.user.service;

import com.revup.annotation.UseCase;
import com.revup.common.skillstack.model.mapper.SkillStackMapper;
import com.revup.user.entity.User;
import com.revup.user.entity.UserSkillStack;
import com.revup.user.model.response.MySkillStacksResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetMyInfoUseCase {

    private final UserService userService;
    private final UserSkillStackService userSkillStackService;
    private final SkillStackMapper skillStackMapper;

    public MySkillStacksResponse executeGetMySkillStack(User user) {
        List<UserSkillStack> myStacks = userSkillStackService.findByUser(user);
        return skillStackMapper.toMySkillStacksResponse(myStacks);
    }
}
