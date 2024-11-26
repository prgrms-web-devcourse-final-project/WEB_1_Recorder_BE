package com.revup.feedback.mapper;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.FeedbackSkillStack;
import org.springframework.stereotype.Component;

@Component
public class FeedbackSkillStackMapper {

    public FeedbackSkillStack toEntity(Feedback feedback, String skillStack) {
        return FeedbackSkillStack.builder()
                .feedback(feedback)
                .skillStack(SkillStack.from(skillStack))
                .build();
    }

}
