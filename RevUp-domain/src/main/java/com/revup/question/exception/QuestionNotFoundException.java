package com.revup.question.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class QuestionNotFoundException extends AppException {
    public QuestionNotFoundException(Long id) {
        super(ErrorCode.QUESTION_NOT_FOUND, id);
    }
}
