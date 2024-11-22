package com.revup.feedback.adaptor;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackAdaptor {

    private final FeedbackRepository feedbackRepository;

    public Feedback findById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND, feedbackId));
    }

}
