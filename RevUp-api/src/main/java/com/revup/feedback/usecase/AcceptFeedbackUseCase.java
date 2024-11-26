package com.revup.feedback.usecase;

import com.revup.feedback.request.FeedbackAcceptRequest;
import com.revup.feedback.service.FeedbackService;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcceptFeedbackUseCase {

    private final FeedbackService feedbackService;
    private final UserUtil userUtil;

    public Long execute(FeedbackAcceptRequest feedbackAcceptRequest) {
        User currentUser = userUtil.getCurrentUser();
        return feedbackService.feedbackAccept(currentUser, feedbackAcceptRequest.getFeedbackId());
    }

}
