package com.revup.feedback.usecase;

import com.revup.feedback.entity.Mentor;
import com.revup.feedback.request.MentorPageRequest;
import com.revup.feedback.service.MentorService;
import com.revup.feedback.service.response.MentorResponse;
import com.revup.page.Page;
import com.revup.page.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.revup.page.PageUtil.SIZE;

@Component
@RequiredArgsConstructor
public class GetMentorListUseCase {

    private final MentorService mentorService;

    public Page<MentorResponse> execute(MentorPageRequest mentorPageRequest) {
        long totalMentorCount = mentorService.getTotalMentorCount();


        List<Mentor> mentors = mentorService.getMentorsByPage(
                (long) mentorPageRequest.getPage() * SIZE,
                SIZE
        );

        List<MentorResponse> content = mentors.stream()
                .map(MentorResponse::from)
                .toList();

        return PageUtil.createPage(content, mentorPageRequest.getPage(), SIZE);
    }

}
