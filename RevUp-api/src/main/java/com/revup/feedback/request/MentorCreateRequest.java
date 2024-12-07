package com.revup.feedback.request;

import lombok.Data;

import java.util.Set;

@Data
public class MentorCreateRequest {

    private String description;

    private Set<String> skillStacks;

}
