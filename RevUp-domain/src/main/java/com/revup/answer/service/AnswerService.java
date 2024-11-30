package com.revup.answer.service;

import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerCode;
import com.revup.answer.entity.AnswerImage;
import com.revup.answer.exception.AnswerCreationConcurrencyException;
import com.revup.answer.repository.AnswerCodeRepository;
import com.revup.answer.repository.AnswerImageRepository;
import com.revup.answer.repository.AnswerRepository;
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
    private final AnswerCodeRepository answerCodeRepository;

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 500,multiplier = 2.0)
    )
    public Long createAnswer(Long questionId, Answer answer, List<AnswerImage> images, List<AnswerCode> codes) {

        // 질문 조회
        Question question = questionRepository.findByIdWithOptimisticLock(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        // 연관관계 매핑
        answer.assignQuestion(question);
        question.addAnswer(answer);

        // 답변 수 증가
        question.increaseAnswerCount();

        // 답변 저장
        answerRepository.save(answer);

        // 답변 이미지 저장
        answerImageRepository.saveAll(images);

        // 답변 코드 저장
        answerCodeRepository.saveAll(codes);

        return answer.getId();
    }

    @Recover
    public Long recover() {
        throw new AnswerCreationConcurrencyException();
    }
}
