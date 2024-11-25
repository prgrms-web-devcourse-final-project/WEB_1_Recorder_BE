package com.revup.feedback.service;

import com.revup.feedback.entity.MentorSkillStack;
import com.revup.feedback.repository.MentorSkillStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorSkillStackService {

    private final MentorSkillStackRepository mentorSkillStackRepository;

    public MentorSkillStack mentorSkillStackCreate(MentorSkillStack mentorSkillStack) {
        return mentorSkillStackRepository.save(mentorSkillStack);
    }

}
