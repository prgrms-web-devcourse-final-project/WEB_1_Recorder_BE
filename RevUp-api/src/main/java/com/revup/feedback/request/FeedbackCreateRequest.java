package com.revup.feedback.request;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FeedbackCreateRequest {

    private Long teacherId;
    private String type;
    private String title;
    private String githubLink;
    private boolean githubLinkReveal;
    private String description;

    private List<FeedbackCodeCreateRequest> feedbackCodes;

    private Set<String> skillStacks;

}
