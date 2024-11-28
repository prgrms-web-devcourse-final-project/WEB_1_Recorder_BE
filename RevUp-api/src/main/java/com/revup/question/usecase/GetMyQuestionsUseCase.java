package com.revup.question.usecase;

import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.service.QuestionService;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMyQuestionsUseCase {

    private final QuestionService questionService;
    private final UserUtil userUtil;

    private static final int LIMIT = 5;

    public List<QuestionBriefResponse> execute(Long lastId) {
        User currentUser = userUtil.getCurrentUser();

        List<Question> myQuestions = questionService.getMyQuestions(currentUser, lastId, LIMIT);

        return myQuestions.stream()
                .map(QuestionBriefResponse::of)
                .toList();
    }
}
