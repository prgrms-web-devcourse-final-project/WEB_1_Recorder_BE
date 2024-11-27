package com.revup.feedback.request;

import lombok.Data;

import java.util.List;

@Data
public class MentorCreateRequest {

    private String description;

    private List<String> skillStacks;

}
