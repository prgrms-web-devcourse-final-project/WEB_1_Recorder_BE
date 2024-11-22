package com.revup.feedback.service;

import com.revup.feedback.entity.Feedback;
import com.revup.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public Feedback feedbackCreate(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

}
