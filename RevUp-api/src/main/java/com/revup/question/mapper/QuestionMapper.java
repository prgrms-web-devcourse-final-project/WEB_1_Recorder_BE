package com.revup.question.mapper;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.question.dto.request.QuestionUpdateRequest;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionState;
import com.revup.question.entity.QuestionType;
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
                .state(QuestionState.from(request.isTemporary()))
                .stacks(toQuestionStacks(request.stacks()))
                .user(user)
                .build();
    }


    public void updateEntity(QuestionUpdateRequest request, Question question) {
        question.update(
                request.title(),
                QuestionType.of(request.type()),
                QuestionState.from(request.isTemporary()),
                request.content(),
                request.githubLink(),
                BooleanStatus.from(request.githubLinkReveal()),
                BooleanStatus.from(request.isAnonymous()),
                toQuestionStacks(request.stacks())
                );
    }

    private Set<SkillStack> toQuestionStacks(List<String> stacks) {
        return stacks.stream()
                .map(stack -> SkillStack.valueOf(stack.toUpperCase()))
                .collect(Collectors.toSet());
    }
}
