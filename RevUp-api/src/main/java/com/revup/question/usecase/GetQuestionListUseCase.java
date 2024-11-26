package com.revup.question.usecase;

import com.revup.page.Page;
import com.revup.question.util.PageUtil;
import com.revup.question.criteria.QuestionSearchCriteria;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GetQuestionListUseCase {
    private final QuestionService questionService;

    public Page<QuestionBriefResponse> execute(QuestionPageRequest request) {

        //검색 조건 criteria 생성
        QuestionSearchCriteria criteria = new QuestionSearchCriteria(
                request.type(),
                request.state(),
                request.stack(),
                request.keyword());

        long totalQuestions = questionService.getTotalQuestionCount(criteria);


        List<Question> questions = questionService.getQuestionsByPage(
                criteria,
                PageUtil.calculateOffset(request.page()),
                PageUtil.SIZE
        );

        List<QuestionBriefResponse> content = questions.stream()
                .map(QuestionBriefResponse::of)
                .toList();

        return PageUtil.createPage(content, request.page(), totalQuestions);
    }

}
