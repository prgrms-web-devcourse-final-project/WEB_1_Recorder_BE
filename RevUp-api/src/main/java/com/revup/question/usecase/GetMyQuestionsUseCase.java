package com.revup.question.usecase;

import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.service.QuestionService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMyQuestionsUseCase {

    private final QuestionService questionService;

    public List<QuestionBriefResponse> execute(Long lastId,int size,User currentUser) {

        List<Question> myQuestions = questionService.getMyQuestions(currentUser, lastId, size);

        return myQuestions.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }
}
