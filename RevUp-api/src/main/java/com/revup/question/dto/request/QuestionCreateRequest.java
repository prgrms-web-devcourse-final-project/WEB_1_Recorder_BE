package com.revup.question.dto.request;

import com.revup.image.dto.request.QuestionImageRequest;
import com.revup.tag.dto.request.TagRequest;

import java.util.List;

public record QuestionCreateRequest(
        String title,
        String content,
        String type,
        boolean isTemporary,
        boolean isAnonymous,
        List<TagRequest> tags,
        List<QuestionImageRequest> images
) {
}
