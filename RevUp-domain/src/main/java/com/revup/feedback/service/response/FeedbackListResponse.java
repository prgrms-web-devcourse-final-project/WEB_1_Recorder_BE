package com.revup.feedback.service.response;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Feedback;
import com.revup.feedback.entity.enums.FeedbackState;
import com.revup.feedback.entity.enums.FeedbackType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FeedbackListResponse {

    private Long id;

    private Long studentId;

    private String studentNickName;

    private Long teacherId;

    private String teacherNickName;

    private FeedbackType type;

    private String title;

    private String content;

    private FeedbackState state;

    private Set<SkillStack> stacks;

    public static FeedbackListResponse from(Feedback feedback) {
        return FeedbackListResponse.builder()
                .id(feedback.getId())
                .studentId(feedback.getStudent().getId())
                .studentNickName(feedback.getStudent().getNickname())
                .teacherId(feedback.getTeacher().getId())
                .teacherNickName(feedback.getTeacher().getNickname())
                .type(feedback.getType())
                .title(feedback.getTitle())
                .content(feedback.getDescription())
                .state(feedback.getState())
                .stacks(feedback.getStacks())
                .build();
    }

}
