package com.revup.question.usecase;

import com.revup.question.adaptor.QuestionAdaptor;
import com.revup.question.entity.Question;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.service.QuestionService;
import com.revup.user.adaptor.UserAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteQuestionUseCase {
    private final QuestionService questionService;
    private final UserAdaptor userAdaptor;
    private final QuestionAdaptor questionAdaptor;

    public void execute(Long id) {
        Question question = questionAdaptor.findById(id);

        userAdaptor.checkPermission(question.getUser());

        questionService.delete(question);
    }
}
