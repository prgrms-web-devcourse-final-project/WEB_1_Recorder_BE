package com.revup.feedback.service.response;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.FeedbackSkillStack;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.feedback.entity.enums.FeedbackType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FeedbackResponse {

    private Long id;

    private Long studentId;

    private Long teacherId;

    private FeedbackType type;

    private String title;

    private String githubLink;

    private BooleanStatus githubLinkReveal;

    private String description;

    private FeedbackState state;

    private List<FeedbackCodeResponse> feedbackCodes;

    private List<SkillStack> skillStacks;

    public static FeedbackResponse from(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .studentId(feedback.getStudent().getId())
                .teacherId(feedback.getTeacher().getId())
                .type(feedback.getType())
                .title(feedback.getTitle())
                .githubLink(feedback.getGithubLink())
                .githubLinkReveal(feedback.getGithubLinkReveal())
                .description(feedback.getDescription())
                .state(feedback.getState())
                .feedbackCodes(feedback.getFeedbackCodes().stream().map(FeedbackCodeResponse::from).toList())
                .skillStacks(feedback.getFeedbackSkillStacks().stream().map(FeedbackSkillStack::getSkillStack).toList())
                .build();
    }

}
