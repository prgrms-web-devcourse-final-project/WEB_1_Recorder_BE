package com.revup.feedback.service;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.feedback.repository.FeedbackRepository;
import com.revup.feedback.service.response.FeedbackDetailsResponse;
import com.revup.feedback.service.response.FeedbackListResponse;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
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

    public Long feedbackAccept(User currentUser, Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));
        if (!feedback.getTeacher().equals(currentUser)) throw new AppException(ErrorCode.PERMISSION_DENIED);

        feedback.updateState(FeedbackState.ACCEPTED);
        return feedback.getId();
    }

    public FeedbackDetailsResponse feedbackDetails(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND, feedbackId));
        return FeedbackDetailsResponse.from(feedback);
    }

    @Transactional(readOnly = true)
    public List<FeedbackListResponse> feedbackWaitingList(User currentUser) {
        return feedbackRepository.findByTeacherAndState(currentUser, FeedbackState.WAITING_ACCEPTANCE)
                .stream()
                .map(FeedbackListResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackListResponse> feedbackSubmittedList(User currentUser) {
        return feedbackRepository.findByTeacher(currentUser)
                .stream()
                .map(FeedbackListResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedbackListResponse> feedbackAcceptedList(User currentUser) {
        return feedbackRepository.findByTeacherAndState(currentUser, FeedbackState.ACCEPTED)
                .stream()
                .map(FeedbackListResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Integer feedbackCount(User user) {
        return feedbackRepository.countByTeacherAndState(user, FeedbackState.ACCEPTED);
    }

}
