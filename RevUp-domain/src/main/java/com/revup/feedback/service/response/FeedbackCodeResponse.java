package com.revup.feedback.service.response;

import com.revup.feedback.entity.FeedbackCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FeedbackCodeResponse {

    private String name;

    private String content;

    public static FeedbackCodeResponse from(FeedbackCode feedbackCode) {
        return FeedbackCodeResponse.builder()
                .name(feedbackCode.getName())
                .content(feedbackCode.getContent())
                .build();
    }

}
