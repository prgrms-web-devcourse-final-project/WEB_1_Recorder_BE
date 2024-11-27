package com.revup.feedback.request;

import lombok.Data;

@Data
public class FeedbackCodeCreateRequest {

    private Long feedbackId;
    private String name;
    private String content;

}
