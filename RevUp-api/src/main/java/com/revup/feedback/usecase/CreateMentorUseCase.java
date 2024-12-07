package com.revup.feedback.usecase;

import com.revup.feedback.mapper.MentorMapper;
import com.revup.feedback.request.MentorCreateRequest;
import com.revup.feedback.service.MentorService;
import com.revup.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMentorUseCase {

    private final MentorMapper mentorMapper;
    private final MentorService mentorService;

    public Long execute(MentorCreateRequest mentorCreateRequest,User currentUser
    ) {
        return mentorService.mentorCreate(
                mentorMapper.toEntity(currentUser, mentorCreateRequest)
        ).getId();
    }

}
