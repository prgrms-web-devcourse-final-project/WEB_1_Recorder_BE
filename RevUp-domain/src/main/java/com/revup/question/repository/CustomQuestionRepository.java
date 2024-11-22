package com.revup.question.repository;

import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionTag;
import com.revup.question.entity.QuestionType;

import java.util.List;

public interface CustomQuestionRepository {

    List<Question> findQuestionsByType(QuestionType type, long offset, int size);

    long countQuestionsByType(QuestionType type);
}
