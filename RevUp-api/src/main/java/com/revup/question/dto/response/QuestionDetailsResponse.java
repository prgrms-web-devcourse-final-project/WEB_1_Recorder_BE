package com.revup.question.dto.response;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.tag.dto.response.TagNameResponse;

import java.util.List;

public record QuestionDetailsResponse(
        Long id,
        String writer,
        String title,
        String createdAt,
        Long readCount,
        List<TagNameResponse> tags,
        List<AnswerDetailsResponse> answers

) {
}
