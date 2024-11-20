package com.revup.question.dto;

import java.util.List;

public record QuestionCreateRequest(
        String title,
        String content,
        String type,
        String state,
        boolean isAnonymous,
        List<String> categories,
        List<String> imageUrls
) {
}
