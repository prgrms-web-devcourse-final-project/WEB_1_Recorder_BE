package com.revup.question.dto;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.question.enums.QuestionType;

import java.util.Set;

public record QuestionUpdateInfo(
        String title,

        String content,

        BooleanStatus githubLinkReveal,

        String githubLink,

        QuestionType type,

        BooleanStatus isAnonymous,

        Set<SkillStack> stacks
) {
}
