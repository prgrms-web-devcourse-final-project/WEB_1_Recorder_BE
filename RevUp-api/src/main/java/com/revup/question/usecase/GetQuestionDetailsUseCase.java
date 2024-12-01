package com.revup.question.usecase;

import com.revup.question.dto.response.QuestionDetailsResponse;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetQuestionDetailsUseCase {
    private final QuestionService questionService;

    public QuestionDetailsResponse execute(Long id) {

        return QuestionDetailsResponse.of(questionService.getQuestionDetailsWithIncrement(id));
    }

}
