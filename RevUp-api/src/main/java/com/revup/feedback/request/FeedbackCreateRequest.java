package com.revup.feedback.request;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackCreateRequest {

    private Long studentId;
    private Long teacherId;
    private String type;
    private String title;
    private String githubLink;
    private boolean githubLinkReveal;
    private String description;

    private List<FeedbackCodeCreateRequest> feedbackCodes;

    private List<String> skillStacks;

}
