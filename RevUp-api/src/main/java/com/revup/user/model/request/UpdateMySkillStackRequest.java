package com.revup.user.model.request;

import com.revup.common.SkillStack;

public record UpdateMySkillStackRequest(
        String name
) {
    public SkillStack toSkillStack() {
        return SkillStack.from(name);
    }
}
