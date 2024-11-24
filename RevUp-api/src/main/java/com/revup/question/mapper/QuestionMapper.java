package com.revup.question.mapper;

import com.revup.common.BooleanStatus;
import com.revup.image.dto.request.QuestionImageRequest;
import com.revup.image.entity.QuestionImage;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionState;
import com.revup.question.entity.QuestionType;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class QuestionMapper {
    public Question toEntity(QuestionCreateRequest request, User user) {
        return Question.builder()
                .title(request.title())
                .content(request.content())
                .type(QuestionType.of(request.type()))
                .isAnonymous(BooleanStatus.from(request.isAnonymous()))
                .state(QuestionState.PENDING)
                .user(user)
                .build();
    }

    public List<QuestionImage> toQuestionImages(List<QuestionImageRequest> images, Question question) {
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

}
