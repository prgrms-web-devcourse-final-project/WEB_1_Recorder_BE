package com.revup.feedback.service;

import com.revup.feedback.entity.Mentor;
import com.revup.feedback.repository.MentorRepository;
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

    public long getTotalMentorCount() {
        return mentorRepository.count();
    }

    public List<Mentor> getMentorsByPage(long offset, int size) {
        return mentorRepository.findMentorsByPageAndSize(offset, size);
    }

}
