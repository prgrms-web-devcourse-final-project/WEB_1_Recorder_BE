package com.revup.feedback.usecase;

import com.revup.feedback.mapper.FeedbackCodeMapper;
import com.revup.feedback.mapper.FeedbackSkillStackMapper;
import com.revup.feedback.request.FeedbackCodeCreateRequest;
import com.revup.feedback.request.FeedbackCreateRequest;
import com.revup.feedback.mapper.FeedbackMapper;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.service.FeedbackCodeService;
import com.revup.feedback.service.FeedbackService;
import com.revup.feedback.service.FeedbackSkillStackService;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateFeedbackUseCase {

    private final FeedbackService feedbackService;
    private final FeedbackCodeService feedbackCodeService;
    private final FeedbackSkillStackService feedbackSkillStackService;

    private final FeedbackMapper feedbackMapper;
    private final FeedbackCodeMapper feedbackCodeMapper;
    private final FeedbackSkillStackMapper feedbackSkillStackMapper;

    private final UserAdaptor userAdaptor;

    public Long execute(FeedbackCreateRequest feedbackCreateRequest) {
        // TODO: 시큐리티 써서 로그인된 사용자 넣는걸로 바꿔야함
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

        for (String s : feedbackCreateRequest.getSkillStacks()) {
            feedbackSkillStackService.feedbackSkillStackCreate(feedbackSkillStackMapper.toEntity(feedback, s));
        }

        return feedback.getId();
    }

}
