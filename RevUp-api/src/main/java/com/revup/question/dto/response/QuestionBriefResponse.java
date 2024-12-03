package com.revup.question.dto.response;

import com.revup.common.BooleanStatus;
import com.revup.question.entity.Question;

import java.util.List;

public record QuestionBriefResponse(
        Long id,
        String writer,
        String profileImage,
        String title,
        String content,
        String createdAt,
        int answerCount,
        int readCount,
        List<String> stacks
) {
    public static QuestionBriefResponse of(Question question) {
        return new QuestionBriefResponse(
                question.getId(),
                question.getIsAnonymous().equals(BooleanStatus.FALSE) ? question.getUser().getNickname() : "익명",
                question.getUser().getProfileImage(),
                question.getTitle(),
                question.getContent(),
                question.getCreatedAt().toString(),
                question.getAnswerCount(),
                question.getReadCount(),
                question.getStacks()
                        .stream()
                        .map(Enum::toString)
                        .toList()
        );
    }

}
