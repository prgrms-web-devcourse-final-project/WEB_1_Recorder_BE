package com.revup.feedback.usecase;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.adaptor.FeedbackAdaptor;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.FeedbackCode;
import com.revup.feedback.mapper.FeedbackCodeMapper;
import com.revup.feedback.request.FeedbackCodeCreateRequest;
import com.revup.feedback.service.FeedbackCodeService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFeedbackCodeUseCase {

    private final FeedbackCodeService feedbackCodeService;
    private final FeedbackAdaptor feedbackAdaptor;
    private final FeedbackCodeMapper feedbackCodeMapper;

    public Long execute(FeedbackCodeCreateRequest feedbackCodeCreateRequest,
                        User currentUser) {
        Feedback feedback = feedbackAdaptor.findById(feedbackCodeCreateRequest.getFeedbackId());
        if (!feedback.getStudent().equals(currentUser)) throw new AppException(ErrorCode.PERMISSION_DENIED);

        FeedbackCode feedbackCode = feedbackCodeMapper.toEntity(feedback, feedbackCodeCreateRequest);
        return feedbackCodeService.feedbackCodeCreate(feedbackCode);
    }

}
