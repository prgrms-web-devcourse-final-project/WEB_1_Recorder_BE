package com.revup.feedback.service.response;

import com.revup.common.BooleanStatus;
import com.revup.common.SkillStack;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.feedback.entity.enums.FeedbackType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FeedbackDetailsResponse {

    private Long id;

    private Long studentId;

    private String studentNickName;

    private Long teacherId;

    private String teacherNickName;

    private FeedbackType type;

    private String title;

    private String githubLink;

    private BooleanStatus githubLinkReveal;

    private String content;

    private FeedbackState state;

    private List<FeedbackCodeResponse> feedbackCodes;

    private Set<SkillStack> stacks;

    public static FeedbackDetailsResponse from(Feedback feedback) {
        return FeedbackDetailsResponse.builder()
                .id(feedback.getId())
                .studentId(feedback.getStudent().getId())
                .studentNickName(feedback.getStudent().getNickname())
                .teacherId(feedback.getTeacher().getId())
                .teacherNickName(feedback.getTeacher().getNickname())
                .type(feedback.getType())
                .title(feedback.getTitle())
                .githubLink(feedback.getGithubLink())
                .githubLinkReveal(feedback.getGithubLinkReveal())
                .content(feedback.getDescription())
                .state(feedback.getState())
                .feedbackCodes(feedback.getFeedbackCodes().stream().map(FeedbackCodeResponse::from).toList())
                .stacks(feedback.getStacks())
                .build();
    }

}
