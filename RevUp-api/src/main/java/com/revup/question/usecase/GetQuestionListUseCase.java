package com.revup.question.usecase;

import com.revup.page.Page;
import com.revup.question.dto.common.PageInfo;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.revup.question.constatnt.PageConstant.GROUP_SIZE;
import static com.revup.question.constatnt.PageConstant.SIZE;

@Component
@RequiredArgsConstructor
public class GetQuestionListUseCase {
    private final QuestionService questionService;

    public Page<QuestionBriefResponse> getQuestionList(QuestionPageRequest request) {
        long totalQuestions = questionService.getTotalQuestionCount(QuestionType.of(request.type()));

        PageInfo pageInfo = calculatePaginationInfo(request.page(), totalQuestions);


        List<Question> questions = questionService.getQuestionsByPage(
                QuestionType.of(request.type()),
                (long)request.page()*SIZE,
                SIZE
        );

        List<QuestionBriefResponse> content = questions.stream()
                .map(QuestionBriefResponse::of)
                .toList();

        return new Page<>(
                content,
                request.page(),
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
