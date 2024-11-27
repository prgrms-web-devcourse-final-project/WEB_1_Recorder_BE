package com.revup.feedback.mapper;

import com.revup.feedback.entity.Mentor;
import com.revup.feedback.request.MentorCreateRequest;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MentorMapper {

    public Mentor toEntity(User user, MentorCreateRequest mentorCreateRequest) {
        return Mentor.builder()
                .user(user)
                .description(mentorCreateRequest.getDescription())
                .build();
    }

}
