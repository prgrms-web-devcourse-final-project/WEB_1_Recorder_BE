package com.revup.question.dto.response;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.question.entity.Question;

import java.util.List;

public record QuestionDetailsResponse(
        Long id,
        String writer,
        String title,
        String createdAt,
        List<String> stacks,
        List<AnswerDetailsResponse> answers
) {
    public static QuestionDetailsResponse of(Question question) {
        return new QuestionDetailsResponse(
                question.getId(),
                question.getUser().getNickname(),
                question.getTitle(),
                question.getCreatedAt().toString(),
                question.getStacks()
                        .stream()
                        .map(Enum::toString)
                        .toList(),
                question.getAnswers()
                        .stream()
                        .map(AnswerDetailsResponse::of)
                        .toList()
        );
    }
}
