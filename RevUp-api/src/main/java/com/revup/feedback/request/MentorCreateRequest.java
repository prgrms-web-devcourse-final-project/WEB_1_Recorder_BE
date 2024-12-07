package com.revup.feedback.request;

import lombok.Data;

import java.util.Set;

@Data
public class MentorCreateRequest {

    private String title;

    private String content;

    private Set<String> skillStacks;

}
