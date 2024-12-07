package com.revup.answer.dto.response;

import com.revup.answer.entity.Answer;

public record AnswerDetailsResponse(
        Long id,
        String content,
        String code,
        String writer,
        String profileImage,
        int  totalAnswerCount,
        int adoptedRate,
        String createdAt,
        boolean isAccept,
        int goodCount,
        int badCount
) {
    public static AnswerDetailsResponse of(Answer answer){
        return new AnswerDetailsResponse(
                answer.getId(),
                answer.getContent(),
                answer.getCode(),
                answer.getUser().getNickname(),
                answer.getUser().getProfileImage(),
                answer.getUser().getTotalAnswerCount(),
                answer.getUser().getAdoptedRate().intValue(),
                answer.getCreatedAt().toString(),
                answer.getIsAccept().toBoolean(),
                answer.getGoodCount(),
                answer.getBadCount()
        );
    }

}
