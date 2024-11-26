package com.revup.question.usecase;

import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteQuestionUseCase {
    private final QuestionService questionService;

    public void execute(Long id) {
        questionService.delete(id);
    }
}
