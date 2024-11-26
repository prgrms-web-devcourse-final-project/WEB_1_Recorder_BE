package com.revup.question.service;

import com.revup.image.entity.QuestionImage;
import com.revup.image.repository.QuestionImageRepository;
import com.revup.question.criteria.QuestionSearchCriteria;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import com.revup.question.exception.QuestionNotFoundException;
import com.revup.question.repository.QuestionCodeRepository;
import com.revup.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionImageRepository questionImageRepository;
    private final QuestionCodeRepository questionCodeRepository;

    @Transactional
    public Long createQuestion(Question question, List<QuestionImage> images, List<QuestionCode> codes) {

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

    public Question getQuestionDetails(Long id) {
        return questionRepository.findByIdWithTagsAndAnswers(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @Transactional
    public void updateImages(Long id, List<QuestionImage> images) {
        questionImageRepository.deleteByQuestionId(id);
        questionImageRepository.saveAll(images);
    }

    @Transactional
    public void updateCodes(Long id, List<QuestionCode> codes) {
        questionCodeRepository.deleteByQuestionId(id);
        questionCodeRepository.saveAll(codes);
    }

    @Transactional
    public void delete(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        question.softDelete();
    }
}
