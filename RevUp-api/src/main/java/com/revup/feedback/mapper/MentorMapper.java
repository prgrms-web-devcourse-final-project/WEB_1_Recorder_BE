package com.revup.feedback.mapper;

import com.revup.common.SkillStack;
import com.revup.feedback.entity.Mentor;
import com.revup.feedback.request.MentorCreateRequest;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MentorMapper {

    public Mentor toEntity(User user, MentorCreateRequest mentorCreateRequest) {
        return Mentor.builder()
                .user(user)
                .title(mentorCreateRequest.getTitle())
                .content(mentorCreateRequest.getContent())
                .stacks(toMentorStacks(mentorCreateRequest.getSkillStacks()))
                .build();
    }

    private Set<SkillStack> toMentorStacks(Set<String> stackStrings) {
        return stackStrings.stream()
                .map(s -> SkillStack.valueOf(s.toUpperCase()))
                .collect(Collectors.toSet());
    }

}
