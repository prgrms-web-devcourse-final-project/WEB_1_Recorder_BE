package com.revup.question.dto.response;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.question.entity.Question;

import java.util.List;

public record QuestionDetailsResponse(
        Long id,
        String writer,
        String title,
        String createdAt,
        int answerCount,
        int readCount,
        List<String> stacks,
        List<QuestionCodeResponse> codes,
        List<AnswerDetailsResponse> answers
) {
    public static QuestionDetailsResponse of(Question question) {
        return new QuestionDetailsResponse(
                question.getId(),
                question.getUser().getNickname(),
                question.getTitle(),
                question.getCreatedAt().toString(),
                question.getAnswerCount(),
                question.getReadCount(),
                question.getStacks()
                        .stream()
                        .map(Enum::toString)
                        .toList(),
                question.getCodes()
                        .stream()
                        .map(QuestionCodeResponse::of)
                        .toList(),
                question.getAnswers()
                        .stream()
                        .map(AnswerDetailsResponse::of)
                        .toList()
        );
    }
}
