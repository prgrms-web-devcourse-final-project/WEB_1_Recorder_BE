package com.revup.question.dto.request;

public record QuestionPageRequest(
        String type,
        Long page,
        Long size
) {
}
