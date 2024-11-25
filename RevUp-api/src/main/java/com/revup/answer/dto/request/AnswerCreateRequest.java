package com.revup.answer.dto.request;

import com.revup.image.dto.request.ImageRequest;

import java.util.List;

public record AnswerCreateRequest(
        Long questionId,
        String title,
        String content,
        List<ImageRequest> images
) {
}
