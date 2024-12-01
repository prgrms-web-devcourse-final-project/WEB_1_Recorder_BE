package com.revup.question.service;

import com.revup.answer.enums.AdoptedReview;
import com.revup.answer.entity.Answer;
import com.revup.answer.exception.AnswerAlreadyAdoptedException;
import com.revup.answer.exception.AnswerNotFoundException;
import com.revup.answer.exception.AnswerNotLinkedException;
import com.revup.answer.repository.AnswerRepository;
import com.revup.question.dto.QuestionUpdateInfo;
import com.revup.question.entity.QuestionImage;
import com.revup.question.enums.QuestionState;
import com.revup.question.exception.QuestionAlreadyAcceptException;
import com.revup.question.repository.QuestionImageRepository;
import com.revup.question.dto.QuestionSearchCriteria;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.repository.QuestionCodeRepository;
import com.revup.question.repository.QuestionRepository;
import com.revup.user.entity.User;
import com.revup.user.exception.UserPermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionImageRepository questionImageRepository;
    private final QuestionCodeRepository questionCodeRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long createQuestion(Question question, List<QuestionImage> images, List<QuestionCode> codes) {
        // 연관관계 매핑
        for (QuestionCode code : codes) {
            question.addQuestionCode(code);

        }
        questionRepository.save(question);

        questionImageRepository.saveAll(images);

        questionCodeRepository.saveAll(codes);

        return question.getId();

    }

    public List<Question> getQuestionsByPage(QuestionSearchCriteria criteria, long offset, int size) {
        return questionRepository.findQuestionsByCriteria(criteria, offset, size);
    }

    public long getTotalQuestionCount(QuestionSearchCriteria criteria) {
        return questionRepository.countQuestionsByCriteria(criteria);
    }


    @Transactional
    public Question getQuestionDetailsWithIncrement(Long id) {
        Question question = questionRepository.findByIdWithStacksAndAnswersAndCodes(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
        question.increaseReadCount();

        return question;
    }

    public List<Question> getPopularQuestions(int limit, int days) {
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        return questionRepository.findQuestionsByReadCountAndAnswerCount(limit, from);

    }

    public List<Question> getRecentQuestions(int limit) {
        return questionRepository.findQuestionsByCreatedAt(limit);
    }

    public List<Question> getByStack(int limit, String stack) {
        return questionRepository.findQuestionsByStack(limit, stack);
    }

    public List<Question> getMyQuestions(User currentUser, Long lastId, int limit) {
        return questionRepository.findQuestionsByUserAndLastId(currentUser, lastId, limit);
    }

    @Transactional
    public Long updateQuestion(Long id, User currentUser, QuestionUpdateInfo updateInfo, List<QuestionImage> images, List<QuestionCode> codes) {
        //질문 조회
        Question existQuestion = questionRepository.findByIdWithUser(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        // 권한 검증
        checkPermission(currentUser, existQuestion.getUser());

        // 질문 업데이트
        existQuestion.update(
                updateInfo.title(),
                updateInfo.type(),
                updateInfo.content(),
                updateInfo.githubLink(),
                updateInfo.githubLinkReveal(),
                updateInfo.isAnonymous(),
                updateInfo.stacks()
        );

        // 연관관계 매핑
        for (QuestionImage image : images) {
            image.assignQuestion(existQuestion);
        }

        // 연관관계 매핑
        for (QuestionCode code : codes) {
            code.assignQuestion(existQuestion);
            existQuestion.addQuestionCode(code);
        }

        // 관련 이미지 삭제 후 새로 저장
        questionImageRepository.deleteByQuestionId(id);
        questionImageRepository.saveAll(images);

        //관련 코드 삭제 후 새로 저장
        questionCodeRepository.deleteByQuestionId(id);
        questionCodeRepository.saveAll(codes);

        return existQuestion.getId();

    }


    @Transactional
    public Long adoptAnswer(Long questionId, Long answerId, String review, User currentUser) {
        // 질문 검증
        Question question = validateQuestion(questionId, currentUser);

        // 답변 검증
        Answer answer = validateAnswer(answerId, question);

        // 질문 상태 변경 -> 채택됨으로
        question.adoptAnswer();

        // 답변 채택 여부 및 리뷰 업데이트
        answer.adoptWithReview(AdoptedReview.from(review));

        return question.getId();
    }

    private Question validateQuestion(Long questionId, User currentUser) {
        Question question = questionRepository.findByIdWithUser(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        // 권한 검증
        checkPermission(currentUser, question.getUser());

        // 이미 채택된 질문인지 검증
        if (question.getState().equals(QuestionState.ADOPTED)) {
            throw new QuestionAlreadyAcceptException();
        }

        return question;
    }


    private Answer validateAnswer(Long answerId, Question question) {
        Answer answer = answerRepository.findByIdWithQuestion(answerId)
                .orElseThrow(() -> new AnswerNotFoundException(answerId));

        // 해당 답변이 질문 해당 질문에 달린 답변이 맞는지 검증
        if (!answer.getQuestion().equals(question)) {
            throw new AnswerNotLinkedException();
        }

        // 답변이 이미 채택된 상태인지 검증
        if (answer.getIsAccept().toBoolean()) {
            throw new AnswerAlreadyAdoptedException();
        }


        return answer;
    }

    @Transactional
    public void delete(Long id, User currentUser) {
        // 질문 조회
        Question question = questionRepository.findByIdWithUser(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        // 권한 검증
        checkPermission(currentUser, question.getUser());

        // 질문 삭제
        question.softDelete();
    }

    private void checkPermission(User currenUser, User writer) {
        if (!currenUser.equals(writer)) {
            throw new UserPermissionException();
        }
    }

}
