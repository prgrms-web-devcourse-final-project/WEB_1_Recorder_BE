package com.revup.answer.usecase;

import com.revup.answer.service.AnswerService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAnswerUseCase {
    private final AnswerService answerService;

    public void execute(Long id, User currentUser) {
        answerService.delete(id, currentUser);
    }
}
