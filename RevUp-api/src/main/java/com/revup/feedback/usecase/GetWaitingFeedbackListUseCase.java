package com.revup.feedback.usecase;

import com.revup.feedback.service.FeedbackService;
import com.revup.feedback.service.response.FeedbackDetailsResponse;
import com.revup.feedback.service.response.FeedbackListResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetWaitingFeedbackListUseCase {

    private final FeedbackService feedbackService;

    public List<FeedbackListResponse> execute(User currentUser) {
        return feedbackService.feedbackWaitingList(currentUser);
    }

}
