package com.revup.question.dto;

public record QuestionSearchCriteria(
        String type,
        String state,
        String stack,
        String keyword
) {
}
