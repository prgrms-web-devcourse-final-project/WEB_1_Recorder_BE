package com.revup.question.mapper;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.question.dto.QuestionUpdateInfo;
import com.revup.question.dto.request.QuestionUpdateRequest;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.entity.Question;
import com.revup.question.enums.QuestionState;
import com.revup.question.enums.QuestionType;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    public Question toEntity(QuestionCreateRequest request, User user) {
        return Question.builder()
                .title(request.title())
                .content(request.content())
                .githubLink(request.githubLink())
                .githubLinkReveal(BooleanStatus.from(request.githubLinkReveal()))
                .type(QuestionType.of(request.type()))
                .isAnonymous(BooleanStatus.from(request.isAnonymous()))
                .state(QuestionState.PENDING)
                .stacks(toQuestionStacks(request.stacks()))
                .user(user)
                .build();
    }


    private Set<SkillStack> toQuestionStacks(List<String> stacks) {
        return stacks.stream()
                .map(SkillStack::from)
                .collect(Collectors.toSet());
    }

    public QuestionUpdateInfo toUpdateInfo(QuestionUpdateRequest request) {
        return new QuestionUpdateInfo(
                request.title(),
                request.content(),
                BooleanStatus.from(request.githubLinkReveal()),
                request.githubLink(),
                QuestionType.of(request.type()),
                BooleanStatus.from(request.isAnonymous()),
                toQuestionStacks(request.stacks())

        );
    }
}
