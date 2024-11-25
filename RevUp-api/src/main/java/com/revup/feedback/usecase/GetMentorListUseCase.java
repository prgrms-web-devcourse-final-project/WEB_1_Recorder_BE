package com.revup.feedback.usecase;

import com.revup.feedback.service.MentorService;
import com.revup.feedback.service.response.MentorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMentorListUseCase {

    private final MentorService mentorService;

    public List<MentorResponse> execute() {
        return mentorService.mentorList();
    }

}
