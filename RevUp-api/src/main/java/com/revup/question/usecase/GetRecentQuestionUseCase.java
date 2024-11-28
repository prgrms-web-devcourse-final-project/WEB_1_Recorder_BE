package com.revup.question.usecase;

import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetRecentQuestionUseCase {
    private final QuestionService questionService;

    // 상위 몇개를 가져올 것인지
    private static final int LIMIT = 5;

    public List<QuestionBriefResponse> execute() {

        List<Question> recentQuestions = questionService.getRecentQuestions(LIMIT);

        return recentQuestions.stream()
                .map(QuestionBriefResponse::of)
                .toList();

    }
}
