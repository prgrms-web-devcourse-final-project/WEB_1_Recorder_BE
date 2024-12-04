package com.revup.common.skillstack.model.mapper;

import com.revup.annotation.Mapper;
import com.revup.common.SkillStack;
import com.revup.common.skillstack.model.response.SkillStackResponse;
import com.revup.user.entity.UserSkillStack;
import com.revup.user.model.response.MySkillStacksResponse;

import java.util.Arrays;
import java.util.List;

@Mapper
public class SkillStackMapper {


    public SkillStackResponse toSkillStackResponse() {
        List<String> skillStacks = Arrays.stream(SkillStack.values())
                .map(SkillStack::getContent)
                .toList();
        return new SkillStackResponse(skillStacks);
    }

    public SkillStackResponse toSkillStackResponse(List<UserSkillStack> userSkillStacks) {
        List<String> skillStacks = userSkillStacks.stream()
                .map(UserSkillStack::getStack)
                .map(SkillStack::getContent)
                .toList();
        return new SkillStackResponse(skillStacks);
    }

    public MySkillStacksResponse toMySkillStacksResponse(List<UserSkillStack> userSkillStacks) {
        return MySkillStacksResponse.of(userSkillStacks);
    }
}
