package com.revup.feedback.controller.command;

import com.revup.feedback.controller.mapper.FeedbackCodeMapper;
import com.revup.feedback.controller.request.FeedbackCodeCreateRequest;
import com.revup.feedback.controller.request.FeedbackCreateRequest;
import com.revup.feedback.controller.mapper.FeedbackMapper;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.service.FeedbackCodeService;
import com.revup.feedback.service.FeedbackService;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackCommand {

    private final FeedbackService feedbackService;
    private final FeedbackCodeService feedbackCodeService;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackCodeMapper feedbackCodeMapper;
    private final UserAdaptor userAdaptor;

    public Long feedbackCreateCommand(FeedbackCreateRequest feedbackCreateRequest) {
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
