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

    private String nickName;

    private String profileImage;

    private String title;

    private String content;

    Set<SkillStack> skillStacks;

    private double answerAcceptanceRate;

    private Integer liveFeedbackCount;

    public static MentorResponse from(Mentor mentor, Integer feedbackCount) {
        return MentorResponse.builder()
                .userId(mentor.getUser().getId())
                .nickName(mentor.getUser().getNickname())
                .profileImage(mentor.getUser().getProfileImage())
                .title(mentor.getTitle())
                .content(mentor.getContent())
                .skillStacks(mentor.getStacks())
                .answerAcceptanceRate(mentor.getUser().getAdoptedRate())
                .liveFeedbackCount(feedbackCount)
                .build();
    }

}
