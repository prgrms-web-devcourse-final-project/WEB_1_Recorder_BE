package com.revup.feedback.service.response;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Mentor;
import com.revup.feedback.entity.MentorSkillStack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class MentorResponse {

    private String description;

    private String nickname;

    List<SkillStack> skillStacks;

    public static MentorResponse from(Mentor mentor) {
        return MentorResponse.builder()
                .description(mentor.getDescription())
                .nickname(mentor.getUser().getSocialId())
                .skillStacks(mentor.getMentorSkillStacks().stream().map(MentorSkillStack::getSkillStack).toList())
                .build();
    }

}
