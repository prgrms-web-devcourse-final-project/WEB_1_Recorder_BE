package com.revup.feedback.usecase;

import com.revup.feedback.entity.Mentor;
import com.revup.feedback.request.MentorPageRequest;
import com.revup.feedback.service.MentorService;
import com.revup.feedback.service.response.MentorResponse;
import com.revup.page.Page;
import com.revup.question.dto.common.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.revup.question.constatnt.PageConstant.GROUP_SIZE;
import static com.revup.question.constatnt.PageConstant.SIZE;

@Component
@RequiredArgsConstructor
public class GetMentorListUseCase {

    private final MentorService mentorService;

    public Page<MentorResponse> execute(MentorPageRequest mentorPageRequest) {
        long totalQuestions = mentorService.getTotalMentorCount();

        PageInfo pageInfo = calculatePaginationInfo(mentorPageRequest.getPage(), totalQuestions);

        List<Mentor> mentors = mentorService.getMentorsByPage(
                (long) mentorPageRequest.getPage() * SIZE,
                SIZE
        );

        List<MentorResponse> content = mentors.stream()
                .map(MentorResponse::from)
                .toList();

        return new Page<>(
                content,
                mentorPageRequest.getPage(),
                content.size(),
                pageInfo.startPage(),
                pageInfo.endPage(),
                pageInfo.prev(),
                pageInfo.next()
        );
    }

    private PageInfo calculatePaginationInfo(int currentPage, long totalQuestions){
        int currentGroup = currentPage / GROUP_SIZE;
        int startPage = currentGroup * GROUP_SIZE + 1;

        int endPage = Math.min(
                startPage + GROUP_SIZE - 1,
                (int) Math.ceil((double) totalQuestions / SIZE)
        );

        boolean prev = startPage > 1;
        boolean next = (long) endPage * SIZE < totalQuestions;

        return new PageInfo(startPage, endPage, prev, next);
    }

}
