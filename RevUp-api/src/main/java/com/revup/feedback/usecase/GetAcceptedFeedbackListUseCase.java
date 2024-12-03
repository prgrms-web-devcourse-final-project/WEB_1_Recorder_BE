package com.revup.feedback.usecase;

import com.revup.feedback.service.FeedbackService;
import com.revup.feedback.service.response.FeedbackResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAcceptedFeedbackListUseCase {

    private final FeedbackService feedbackService;

    public List<FeedbackResponse> execute(User currentUser) {
        return feedbackService.feedbackAcceptedList(currentUser);
    }

}
