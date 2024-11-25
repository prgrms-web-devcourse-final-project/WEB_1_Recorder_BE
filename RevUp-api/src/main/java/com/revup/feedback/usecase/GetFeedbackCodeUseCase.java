package com.revup.feedback.usecase;

import com.revup.feedback.service.FeedbackCodeService;
import com.revup.feedback.service.response.FeedbackCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetFeedbackCodeUseCase {

    private final FeedbackCodeService feedbackCodeService;

    public FeedbackCodeResponse execute(Long feedbackCodeId) {
        return feedbackCodeService.feedbackCodeGet(feedbackCodeId);
    }

}
