package com.revup.answer.service;

import com.revup.answer.dto.AnswerUpdateInfo;
import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerCode;
import com.revup.answer.entity.AnswerImage;
import com.revup.answer.exception.AnswerCreationConcurrencyException;
import com.revup.answer.exception.AnswerNotFoundException;
import com.revup.answer.repository.AnswerCodeRepository;
import com.revup.answer.repository.AnswerImageRepository;
import com.revup.answer.repository.AnswerRepository;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import com.revup.question.entity.QuestionImage;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.repository.QuestionRepository;
import com.revup.user.entity.User;
import com.revup.user.exception.UserPermissionException;
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
            backoff = @Backoff(delay = 500, multiplier = 2.0)
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

        // 연관관계 매핑
        for(AnswerCode code : codes){
            answer.addAnswerCode(code);
        }

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

    public Long updateAnswer(Long answerId, AnswerUpdateInfo updateInfo, List<AnswerImage> images, List<AnswerCode> codes, User currentUser) {

        //질문 조회
        Answer existAnswer = answerRepository.findByIdWithUser(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));

        // 권한 검증
        checkPermission(currentUser, existAnswer.getUser());

        // 질문 업데이트
        existAnswer.update(
                updateInfo.title(),
                updateInfo.content()
        );

        // 연관관계 매핑
        for (AnswerImage image : images) {
            image.assignAnswer(existAnswer);
        }

        // 연관관계 매핑
        for (AnswerCode code : codes) {
            code.assignAnswer(existAnswer);
            existAnswer.addAnswerCode(code);
        }

        // 관련 이미지 삭제 후 새로 저장
        answerImageRepository.deleteByAnswerId(answerId);
        answerImageRepository.saveAll(images);

        //관련 코드 삭제 후 새로 저장
        answerCodeRepository.deleteByAnswerId(answerId);
        answerCodeRepository.saveAll(codes);

        return existAnswer.getId();
    }

    private void checkPermission(User currenUser, User writer) {
        if (!currenUser.equals(writer)) {
            throw new UserPermissionException();
        }
    }
}
