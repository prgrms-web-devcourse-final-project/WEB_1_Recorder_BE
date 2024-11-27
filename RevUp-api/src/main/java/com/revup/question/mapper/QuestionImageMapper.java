package com.revup.question.mapper;

import com.revup.image.dto.request.ImageRequest;
import com.revup.question.entity.QuestionImage;
import com.revup.question.entity.Question;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class QuestionImageMapper {

    public List<QuestionImage> toEntities(List<ImageRequest> images, Question question) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(imageRequest -> QuestionImage.builder()
                        .name(imageRequest.name())
                        .imageUrl(imageRequest.imageUrl())
                        .question(question)
                        .build())
                .toList();
    }

}
