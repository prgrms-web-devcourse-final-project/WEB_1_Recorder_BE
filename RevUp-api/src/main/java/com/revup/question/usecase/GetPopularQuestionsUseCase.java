package com.revup.question.usecase;

import com.revup.global.dto.ApiResponse;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPopularQuestionsUseCase {
    private final QuestionService questionService;
    // 상위 몇개를 가져올 것인지
    private static final int LIMIT = 5;
    // 몇 일 전까지
    private static final int DAYS_AGO = 7;

    public List<QuestionBriefResponse> execute() {
        List<Question> popularQuestions = questionService.getPopularQuestions(LIMIT, DAYS_AGO);
        return popularQuestions.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }
}
