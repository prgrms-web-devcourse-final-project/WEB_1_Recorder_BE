package com.revup.question.dto;

public record QuestionPageRequest(
        String type,
        Long page,
        Long size
) {
}
