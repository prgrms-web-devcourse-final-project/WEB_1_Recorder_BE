package com.revup.question.usecase;

import com.revup.question.dto.response.QuestionDetailsResponse;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetQuestionDetailsUseCase {
    private final QuestionService questionService;

    public QuestionDetailsResponse execute(Long id, boolean alreadyViewed) {
        // 이미 조회 한적이 있다면 조회수 증가 없이 조회
        if (alreadyViewed) {
           return QuestionDetailsResponse.of(questionService.getQuestionDetails(id));
        }

        return QuestionDetailsResponse.of(questionService.getQuestionDetailsWithIncrement(id));
    }

}
