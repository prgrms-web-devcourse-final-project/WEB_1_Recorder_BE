package com.revup.feedback.usecase;

import com.revup.feedback.service.MentorService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteMentorUseCase {

    private final MentorService mentorService;

    public void execute(Long mentorId, User currentUser) {
        mentorService.mentorDelete(mentorId, currentUser);
    }

}
