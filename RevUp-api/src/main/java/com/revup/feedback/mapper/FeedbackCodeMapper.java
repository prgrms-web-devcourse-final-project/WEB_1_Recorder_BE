package com.revup.feedback.mapper;

import com.revup.feedback.request.FeedbackCodeCreateRequest;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.FeedbackCode;
import org.springframework.stereotype.Component;

@Component
public class FeedbackCodeMapper {

    public FeedbackCode toEntity(Feedback feedback, FeedbackCodeCreateRequest feedbackCodeCreateRequest) {
        return FeedbackCode.builder()
                .feedback(feedback)
                .name(feedbackCodeCreateRequest.getName())
                .content(feedbackCodeCreateRequest.getContent())
                .build();
    }

}
