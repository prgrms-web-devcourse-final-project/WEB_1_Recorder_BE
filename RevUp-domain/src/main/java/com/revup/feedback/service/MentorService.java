package com.revup.feedback.service;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;
import com.revup.feedback.entity.Mentor;
import com.revup.feedback.repository.MentorRepository;
import com.revup.user.entity.User;
import com.revup.user.exception.UserPermissionException;
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

    public void mentorDelete(Long mentorId, User currentUser) {
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(() -> new AppException(ErrorCode.MENTOR_NOT_FOUND, mentorId));
        if (!mentor.getUser().equals(currentUser)) throw new UserPermissionException();

        mentor.softDelete();
    }

    @Transactional(readOnly = true)
    public long getTotalMentorCount() {
        return mentorRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Mentor> getMentorsByPage(long offset, int size) {
        return mentorRepository.findMentorsByPageAndSize(offset, size);
    }

}
