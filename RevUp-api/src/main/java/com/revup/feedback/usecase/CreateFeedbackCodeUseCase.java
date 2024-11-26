package com.revup.feedback.usecase;

import com.revup.feedback.adaptor.FeedbackAdaptor;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.FeedbackCode;
import com.revup.feedback.mapper.FeedbackCodeMapper;
import com.revup.feedback.request.FeedbackCodeCreateRequest;
import com.revup.feedback.service.FeedbackCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFeedbackCodeUseCase {

    private final FeedbackCodeService feedbackCodeService;
    private final FeedbackAdaptor feedbackAdaptor;
    private final FeedbackCodeMapper feedbackCodeMapper;

    public Long execute(FeedbackCodeCreateRequest feedbackCodeCreateRequest) {
        // TODO: 자기 피드백 아니면 예외
        Feedback feedback = feedbackAdaptor.findById(feedbackCodeCreateRequest.getFeedbackId());
        FeedbackCode feedbackCode = feedbackCodeMapper.toEntity(feedback, feedbackCodeCreateRequest);
        return feedbackCodeService.feedbackCodeCreate(feedbackCode);
    }

}
