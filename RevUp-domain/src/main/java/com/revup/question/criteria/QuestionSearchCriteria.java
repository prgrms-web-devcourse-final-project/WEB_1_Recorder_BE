package com.revup.question.criteria;

public record QuestionSearchCriteria(
        String type,
        String state,
        String stack,
        String keyword
) {
}
