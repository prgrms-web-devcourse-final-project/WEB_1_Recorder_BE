package com.revup.feedback.service;

import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.feedback.repository.FeedbackRepository;
import com.revup.feedback.service.response.FeedbackResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public Feedback feedbackCreate(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<FeedbackResponse> feedbackWaitingList(User currentUser) {
        return feedbackRepository.findByTeacherAndState(currentUser, FeedbackState.WAITING_ACCEPTANCE)
                .stream()
                .map(FeedbackResponse::from).toList();
    }

    public List<FeedbackResponse> feedbackSubmittedList(User currentUser) {
        return feedbackRepository.findByTeacher(currentUser)
                .stream()
                .map(FeedbackResponse::from).toList();
    }

}
