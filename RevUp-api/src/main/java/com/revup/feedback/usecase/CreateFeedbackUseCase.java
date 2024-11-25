package com.revup.feedback.usecase;

import com.revup.feedback.mapper.FeedbackCodeMapper;
import com.revup.feedback.request.FeedbackCodeCreateRequest;
import com.revup.feedback.request.FeedbackCreateRequest;
import com.revup.feedback.mapper.FeedbackMapper;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.service.FeedbackCodeService;
import com.revup.feedback.service.FeedbackService;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFeedbackUseCase {

    private final FeedbackService feedbackService;
    private final FeedbackCodeService feedbackCodeService;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackCodeMapper feedbackCodeMapper;
    private final UserAdaptor userAdaptor;

    public Long execute(FeedbackCreateRequest feedbackCreateRequest) {
        User student = userAdaptor.findById(feedbackCreateRequest.getStudentId());
        User teacher = userAdaptor.findById(feedbackCreateRequest.getTeacherId());

        Feedback feedback = feedbackService.feedbackCreate(
                feedbackMapper.toEntity(student, teacher, feedbackCreateRequest)
        );

        for (FeedbackCodeCreateRequest dto : feedbackCreateRequest.getFeedbackCodes()) {
            feedbackCodeService.feedbackCodeCreate(
                    feedbackCodeMapper.toEntity(feedback, dto)
            );
        }

        return feedback.getId();
    }

}
