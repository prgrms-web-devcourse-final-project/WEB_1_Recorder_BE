package com.revup.question.dto;

import com.revup.question.entity.QuestionType;

public record QuestionPageInfo(
        QuestionType type,
        Long page,
        Long size
) {
    public static QuestionPageInfo of(
            String type, Long page, Long size
    ) {
        return new QuestionPageInfo(
                QuestionType.valueOf(type),
                page,
                size
        );

    }
}
