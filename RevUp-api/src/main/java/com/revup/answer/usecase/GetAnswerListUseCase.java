package com.revup.answer.usecase;

import com.revup.answer.dto.response.AnswerDetailsResponse;
import com.revup.answer.entity.Answer;
import com.revup.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAnswerListUseCase {
    private final AnswerService answerService;

    public List<AnswerDetailsResponse> execute(Long questionId) {
        List<Answer> answers = answerService.getByQuestionId(questionId);
        return answers.stream()
                .map(AnswerDetailsResponse::of)
                .toList();
    }
}
