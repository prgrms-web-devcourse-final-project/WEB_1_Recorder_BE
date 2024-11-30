package com.revup.question.usecase;

import com.revup.question.service.QuestionService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteQuestionUseCase {
    private final QuestionService questionService;

    public void execute(Long id, User currentUser) {

        questionService.delete(id, currentUser);
    }
}
