package com.revup.question.dto.response;

import com.revup.question.entity.Question;
import com.revup.tag.dto.response.TagNameResponse;

import java.util.List;

public record QuestionBriefResponse(
        Long id,
        String writer,
        String title,
        String createdAt,
        Long readCount,
        List<TagNameResponse> tags
) {
    public static QuestionBriefResponse of(Question question){
        return new QuestionBriefResponse(
                question.getId(),
                question.getUser().getNickname(),
                question.getTitle(),
                question.getCreatedAt().toString(),
                question.getReadCount(),
                question.getQuestionTags()
                        .stream()
                        .map(questionTag -> TagNameResponse.of(questionTag.getTag()))
                        .toList()
        );
    }
}
