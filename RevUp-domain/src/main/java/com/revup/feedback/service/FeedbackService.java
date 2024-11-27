package com.revup.feedback.service;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public Feedback feedbackCreate(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Long feedbackAccept(User currentUser, Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));
        if (!feedback.getTeacher().equals(currentUser)) throw new AppException(ErrorCode.PERMISSION_DENIED);

        feedback.updateState(FeedbackState.ACCEPTED);
        return feedback.getId();
    }

    @Transactional(readOnly = true)
    public List<FeedbackResponse> feedbackWaitingList(User currentUser) {
        return feedbackRepository.findByTeacherAndState(currentUser, FeedbackState.WAITING_ACCEPTANCE)
                .stream()
                .map(FeedbackResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackResponse> feedbackSubmittedList(User currentUser) {
        return feedbackRepository.findByTeacher(currentUser)
                .stream()
                .map(FeedbackResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackResponse> feedbackAcceptedList(User currentUser) {
        return feedbackRepository.findByTeacherAndState(currentUser, FeedbackState.ACCEPTED)
                .stream()
                .map(FeedbackResponse::from).toList();
    }

}
