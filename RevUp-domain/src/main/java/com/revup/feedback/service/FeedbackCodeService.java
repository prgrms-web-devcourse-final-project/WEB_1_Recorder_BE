package com.revup.feedback.service;

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

    public Long feedbackCodecreate(FeedbackCode feedbackCode) {
        return feedbackCodeRepository.save(feedbackCode).getId();
    }

}
