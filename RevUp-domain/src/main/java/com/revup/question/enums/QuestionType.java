package com.revup.question.enums;

import com.revup.question.exception.InvalidQuestionTypeException;

public enum QuestionType {
    DEBUG, REFACTOR;

    public static QuestionType of(String type) {
        try {
            return QuestionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidQuestionTypeException();
        }
    }
}
