package com.revup.user.model.response;

import com.revup.user.entity.UserSkillStack;

import java.util.List;

public record MySkillStacksResponse(
        List<MySkillStackResponse> stacks
) {

    public static MySkillStacksResponse of(List<UserSkillStack> skillStacks) {
        List<MySkillStackResponse> list = skillStacks.stream()
                .map(skillStack ->
                        new MySkillStackResponse(
                                skillStack.getId(),
                                skillStack.getStack().getContent()
                        )
                )
                .toList();
        return new MySkillStacksResponse(list);
    }
}
