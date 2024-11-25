package com.revup.answer.mapper;

import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.entity.Answer;
import com.revup.common.BooleanStatus;
import com.revup.image.dto.request.ImageRequest;
import com.revup.image.entity.AnswerImage;
import com.revup.question.entity.Question;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AnswerMapper {
    public Answer toEntity(AnswerCreateRequest request, User user) {
        return Answer.builder()
                .title(request.title())
                .content(request.content())
                .isAccept(BooleanStatus.FALSE)
                .user(user)
                .build();
    }

    public List<AnswerImage> toAnswerImages(List<ImageRequest> images, Answer answer) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(imageRequest -> AnswerImage.builder()
                        .imageUrl(imageRequest.imageUrl())
                        .answer(answer)
                        .build())
                .toList();
    }
}
