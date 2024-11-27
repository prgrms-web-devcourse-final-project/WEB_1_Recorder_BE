package com.revup.feedback.mapper;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Mentor;
import com.revup.feedback.entity.MentorSkillStack;
import org.springframework.stereotype.Component;

@Component
public class MentorSkillStackMapper {

    public MentorSkillStack toEntity(Mentor mentor, String skillStack) {
        return MentorSkillStack.builder()
                .mentor(mentor)
                .skillStack(SkillStack.of(skillStack))
                .build();
    }

}
