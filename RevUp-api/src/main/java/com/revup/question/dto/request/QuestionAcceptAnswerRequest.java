package com.revup.question.dto.request;

public record QuestionAcceptAnswerRequest(
        Long questionId,
        Long answerId,
        String review
) {
}
