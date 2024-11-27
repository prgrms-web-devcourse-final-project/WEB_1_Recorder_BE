package com.revup.question.dto.response;

import com.revup.question.entity.QuestionCode;

public record QuestionCodeResponse(
        String name,
        String content
) {
    public static QuestionCodeResponse of(QuestionCode code) {
        return new QuestionCodeResponse(
                code.getName(),
                code.getContent()
        );
    }
}
