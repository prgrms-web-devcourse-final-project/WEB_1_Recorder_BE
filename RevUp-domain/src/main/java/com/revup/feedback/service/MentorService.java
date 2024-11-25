package com.revup.feedback.service;

import com.revup.feedback.entity.Mentor;
import com.revup.feedback.repository.MentorRepository;
import com.revup.feedback.service.response.MentorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorService {

    private final MentorRepository mentorRepository;

    public Mentor mentorCreate(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    public List<MentorResponse> mentorList() {
        return mentorRepository.findAllWithMentorSkillStacksAndUser()
                .stream().map(MentorResponse::from)
                .toList();
    }

}
