package com.revup.question.dto;

import com.revup.question.entity.Question;

import java.util.List;

public record QuestionDetailsResponse(
        Long id,
        String writer,
        String title,
        String content,
        Long readCount,
        List<String> categories
) {
    public static QuestionDetailsResponse of(Question question) {
        return new QuestionDetailsResponse(
                question.getId(),
                question.getUser().getNickname(),
                question.getTitle(),
                question.getContent(),
                question.getReadCount(),

        );
    }
}
