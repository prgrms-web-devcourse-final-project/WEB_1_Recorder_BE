package com.revup.feedback.service;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.entity.FeedbackCode;
import com.revup.feedback.repository.FeedbackCodeRepository;
import com.revup.feedback.service.response.FeedbackCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackCodeService {

    private final FeedbackCodeRepository feedbackCodeRepository;

    @Transactional(readOnly = true)
    public FeedbackCodeResponse getFeedbackCode(Long feedbackCodeId) {
        return FeedbackCodeResponse.from(
                feedbackCodeRepository.findById(feedbackCodeId)
                        .orElseThrow(() -> new RuntimeException("코드 못찾음"))
        );
    }

    public Long feedbackCodeCreate(FeedbackCode feedbackCode) {
        return feedbackCodeRepository.save(feedbackCode).getId();
    }

    public Long feedbackCodeUpdate(Long feedbackCodeId, String content) {
        FeedbackCode feedbackCode = feedbackCodeRepository.findById(feedbackCodeId).orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_CODE_NOT_FOUND, feedbackCodeId));
        feedbackCode.updateContent(content);
        System.out.println("피드백코드 최신화");
        return feedbackCode.getId();
    }

}
