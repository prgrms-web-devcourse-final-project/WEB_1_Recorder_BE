package com.revup.feedback.usecase;

import com.revup.feedback.service.FeedbackService;
import com.revup.feedback.service.response.FeedbackDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetFeedbackUseCase {

    private final FeedbackService feedbackService;

    public FeedbackDetailsResponse execute(Long feedbackId) {
        return feedbackService.feedbackDetails(feedbackId);
    }

}
