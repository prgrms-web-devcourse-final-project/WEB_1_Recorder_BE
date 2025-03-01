package com.revup.feedback.service.response;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Mentor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class MentorResponse {

    private Long mentorId;

    private Long userId;

    private String nickName;

    private String profileImage;

    private String title;

    private String content;

    Set<SkillStack> skillStacks;

    private BigDecimal answerAcceptanceRate;

    private Integer liveFeedbackCount;

    public static MentorResponse from(Mentor mentor, Integer feedbackCount) {
        return MentorResponse.builder()
                .mentorId(mentor.getId())
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
