package com.revup.question.dto.response;

import com.revup.question.entity.Question;

public record QuestionBriefResponse(
        Long id,
        String writer,
        String title,
        String createdAt,
        Long readCount
) {
    public static QuestionBriefResponse of(Question question){
        return new QuestionBriefResponse(
                question.getId(),
                question.getUser().getNickname(),
                question.getTitle(),
                question.getCreatedAt().toString(),
                question.getReadCount()
        );
    }
}
