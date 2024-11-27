package com.revup.answer.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.exception.AnswerCreationConcurrencyException;
import com.revup.answer.repository.AnswerRepository;
import com.revup.image.entity.AnswerImage;
import com.revup.image.repository.AnswerImageRepository;
import com.revup.question.entity.Question;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AnswerImageRepository answerImageRepository;

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 500,multiplier = 2.0)
    )
    public Long createAnswer(Long questionId, Answer answer, List<AnswerImage> images) {

        Question question = questionRepository.findByIdWithOptimisticLock(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        answer.assignQuestion(question);

        question.increaseAnswerCount();

        answerRepository.save(answer);

        answerImageRepository.saveAll(images);

        return answer.getId();
    }

    @Recover
    public Long recover() {
        throw new AnswerCreationConcurrencyException();
    }
}
