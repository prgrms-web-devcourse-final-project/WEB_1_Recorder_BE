package com.revup.question.entity;

import com.revup.question.exception.InvalidQuestionTypeException;

public enum QuestionType {
    DEBUG, REVIEW;

    public static QuestionType of(String type) {
        if (type == null || type.isEmpty())
            return null;

        try {
            return QuestionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidQuestionTypeException();
        }
    }
}
