package com.revup.feedback.usecase;

import com.revup.feedback.entity.Mentor;
import com.revup.feedback.mapper.MentorMapper;
import com.revup.feedback.mapper.MentorSkillStackMapper;
import com.revup.feedback.request.MentorCreateRequest;
import com.revup.feedback.service.MentorService;
import com.revup.feedback.service.MentorSkillStackService;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMentorUseCase {

    private final UserUtil userUtil;
    private final MentorMapper mentorMapper;
    private final MentorService mentorService;
    private final MentorSkillStackService mentorSkillStackService;
    private final MentorSkillStackMapper mentorSkillStackMapper;

    public Long execute(MentorCreateRequest mentorCreateRequest) {
        User currentUser = userUtil.getCurrentUser();
        Mentor mentor = mentorService.mentorCreate(mentorMapper.toEntity(currentUser, mentorCreateRequest));

        for (String s : mentorCreateRequest.getSkillStacks()) {
            mentorSkillStackService.mentorSkillStackCreate(mentorSkillStackMapper.toEntity(mentor, s));
        }

        return mentor.getId();
    }

}
