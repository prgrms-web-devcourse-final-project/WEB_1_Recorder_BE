package com.revup.question.repository;

import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;

import java.util.List;

public interface CustomQuestionRepository {
    List<Question> findQuestionList(Long page, Long size, QuestionType type);

}
