package com.revup.question.dto.response;

import com.revup.common.BooleanStatus;
import com.revup.question.entity.Question;
import com.revup.tag.dto.response.TagNameResponse;

import java.util.List;

public record QuestionBriefResponse(
        Long id,
        String writer,
        String title,
        String createdAt,
        Long readCount,
        int answerCount,
        List<TagNameResponse> tags
) {
    public static QuestionBriefResponse of(Question question) {
        return new QuestionBriefResponse(
                question.getId(),
                question.getIsAnonymous().equals(BooleanStatus.FALSE) ? question.getUser().getNickname() : "익명",
                question.getTitle(),
                question.getCreatedAt().toString(),
                question.getReadCount(),
                question.getAnswerCount(),
                question.getQuestionTags()
                        .stream()
                        .map(questionTag -> TagNameResponse.of(questionTag.getTag()))
                        .toList()
        );
    }

}
