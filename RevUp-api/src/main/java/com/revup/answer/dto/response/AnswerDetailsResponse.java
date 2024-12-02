package com.revup.answer.dto.response;

import com.revup.answer.entity.Answer;

import java.util.List;

public record AnswerDetailsResponse(
        Long id,
        String title,
        String content,
        String writer,
        String createdAt,
        boolean isAccept,
        int goodCount,
        int badCount,
        List<AnswerCodeResponse> codes
) {
    public static AnswerDetailsResponse of(Answer answer){
        return new AnswerDetailsResponse(
                answer.getId(),
                answer.getTitle(),
                answer.getContent(),
                answer.getUser().getNickname(),
                answer.getCreatedAt().toString(),
                answer.getIsAccept().toBoolean(),
                answer.getGoodCount(),
                answer.getBadCount(),
                answer.getCodes()
                        .stream()
                        .map(AnswerCodeResponse::of)
                        .toList()
        );
    }

}
