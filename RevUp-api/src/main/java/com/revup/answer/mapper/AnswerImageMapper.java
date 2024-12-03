package com.revup.answer.mapper;

import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerImage;
import com.revup.image.dto.request.ImageRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AnswerImageMapper {
    public List<AnswerImage> toEntities(List<ImageRequest> images, Answer answer) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(imageRequest -> AnswerImage.builder()
                        .name(imageRequest.name())
                        .imageUrl(imageRequest.imageUrl())
                        .answer(answer)
                        .build())
                .toList();
    }

    public List<AnswerImage> toUpdateEntity(List<ImageRequest> images) {
        if (images == null || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(imageRequest -> AnswerImage.builder()
                        .name(imageRequest.name())
                        .imageUrl(imageRequest.imageUrl())
                        .build())
                .toList();
    }

}
