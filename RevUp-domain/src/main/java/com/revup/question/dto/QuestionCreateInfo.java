package com.revup.question.dto;

import com.revup.common.BooleanStatus;
import com.revup.question.entity.QuestionState;
import com.revup.question.entity.QuestionType;

import java.util.List;

public record QuestionCreateInfo(
        String title,
        String content,
        QuestionType type,
        BooleanStatus isAnonymous,
        QuestionState state,
        List<String> categories,
        List<String> imageUrls
) {
    public static QuestionCreateInfo of(
            String title,
            String content,
            String type,
            boolean isAnonymous,
            String state,
            List<String> categories,
            List<String> imageUrls
    ){
        return new QuestionCreateInfo(
                title,
                content,
                QuestionType.valueOf(type),
                BooleanStatus.from(isAnonymous),
                QuestionState.valueOf(state),
                categories,
                imageUrls
        );
    }
}
