package com.revup.feedback.service.response;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Mentor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class MentorResponse {

    private Long userId;

    private String description;

    private String nickname;

    Set<SkillStack> skillStacks;

    public static MentorResponse from(Mentor mentor) {
        return MentorResponse.builder()
                .userId(mentor.getUser().getId())
                .description(mentor.getDescription())
                .nickname(mentor.getUser().getSocialId())
                .skillStacks(mentor.getStacks())
                .build();
    }

}
