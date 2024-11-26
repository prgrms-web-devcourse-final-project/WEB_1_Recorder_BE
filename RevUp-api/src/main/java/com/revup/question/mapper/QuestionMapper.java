package com.revup.question.mapper;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.image.dto.request.ImageRequest;
import com.revup.image.entity.QuestionImage;
import com.revup.question.dto.request.QuestionCodeCreateRequest;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import com.revup.question.entity.QuestionState;
import com.revup.question.entity.QuestionType;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    public Question toEntity(QuestionCreateRequest request, User user, Set<SkillStack> stacks) {
        return Question.builder()
                .title(request.title())
                .content(request.content())
                .githubLink(request.githubLink())
                .githubLinkReveal(BooleanStatus.from(request.githubLinkReveal()))
                .type(QuestionType.of(request.type()))
                .isAnonymous(BooleanStatus.from(request.isAnonymous()))
                .state(QuestionState.from(request.isTemporary()))
                .stacks(stacks)
                .user(user)
                .build();
    }
    
    public List<QuestionImage> toQuestionImages(List<ImageRequest> images, Question question) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(imageRequest -> QuestionImage.builder()
                        .imageUrl(imageRequest.imageUrl())
                        .question(question)
                        .build())
                .toList();
    }

    public Set<SkillStack> toQuestionStacks(List<String> stacks) {
        return stacks.stream()
                .map(stack -> SkillStack.valueOf(stack.toUpperCase()))
                .collect(Collectors.toSet());
    }

    public List<QuestionCode> toQuestionCodes(List<QuestionCodeCreateRequest> codes, Question question) {
        return codes.stream()
                .map(code -> QuestionCode.builder()
                        .question(question)
                        .name(code.name())
                        .content(code.content())
                        .build())
                .toList();
    }


}
