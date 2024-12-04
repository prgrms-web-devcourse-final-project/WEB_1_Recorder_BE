package com.revup.answer.dto.response;

import com.revup.answer.entity.Answer;

public record AnswerDetailsResponse(
        Long id,
        String title,
        String content,
        String code,
        String writer,
        String profileImage,
        String createdAt,
        boolean isAccept,
        int goodCount,
        int badCount
) {
    public static AnswerDetailsResponse of(Answer answer){
        return new AnswerDetailsResponse(
                answer.getId(),
                answer.getTitle(),
                answer.getContent(),
                answer.getCode(),
                answer.getUser().getNickname(),
                answer.getUser().getProfile().getProfileImage(),
                answer.getCreatedAt().toString(),
                answer.getIsAccept().toBoolean(),
                answer.getGoodCount(),
                answer.getBadCount()
        );
    }

}
