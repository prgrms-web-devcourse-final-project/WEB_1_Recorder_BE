package com.revup.question.dto.response;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.common.BooleanStatus;
import com.revup.question.entity.Question;

import java.util.List;

public record QuestionDetailsResponse(
        Long id,
        String writer,
        String title,
        String content,
        String createdAt,
        int answerCount,
        int readCount,
        String githubLink,
        List<String> stacks,
        List<QuestionCodeResponse> codes,
        List<AnswerDetailsResponse> answers
) {
    public static QuestionDetailsResponse of(Question question) {
        return new QuestionDetailsResponse(
                question.getId(),
                question.getUser().getNickname(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt().toString(),
                question.getAnswerCount(),
                question.getReadCount(),
                question.getGithubLinkReveal().toBoolean() ? question.getGithubLink() : "링크 비공개",
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
