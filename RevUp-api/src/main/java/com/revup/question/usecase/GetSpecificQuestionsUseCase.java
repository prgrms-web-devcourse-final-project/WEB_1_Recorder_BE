package com.revup.question.usecase;

import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetSpecificQuestionsUseCase {
    private final QuestionService questionService;

    public List<QuestionBriefResponse> getRecent(int size) {

        List<Question> recentQuestions = questionService.getRecentQuestions(size);

        return recentQuestions.stream()
                .map(QuestionBriefResponse::of)
                .toList();

    }

    public List<QuestionBriefResponse> getPopulars(int size,int days) {
        List<Question> popularQuestions = questionService.getPopularQuestions(size, days);
        return popularQuestions.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }

    public List<QuestionBriefResponse> getByStack(int size,String stack){
        List<Question> questionsByStack = questionService.getByStack(size, stack);
        return questionsByStack.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }

}
