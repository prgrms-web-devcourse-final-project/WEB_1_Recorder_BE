package com.revup.question.repository;

import com.revup.question.criteria.QuestionSearchCriteria;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;

import java.util.List;
import java.util.Optional;

public interface CustomQuestionRepository {

    List<Question> findQuestionsByCriteria(QuestionSearchCriteria criteria, long offset, int size);

    long countQuestionsByCriteria(QuestionSearchCriteria criteria);

    Optional<Question> findByIdWithTagsAndAnswers(Long id);
}
