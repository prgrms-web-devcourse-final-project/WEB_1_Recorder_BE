package com.revup.answer.usecase;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.answer.entity.Answer;
import com.revup.answer.service.AnswerService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMyAnswersUseCase {
    private final AnswerService answerService;

    public List<AnswerDetailsResponse> execute(User currentUser, Long lastId, int size) {

        List<Answer> answers = answerService.getMyAnswers(currentUser, lastId, size);

        return answers.stream()
                .map(AnswerDetailsResponse::of)
                .toList();
    }
}
