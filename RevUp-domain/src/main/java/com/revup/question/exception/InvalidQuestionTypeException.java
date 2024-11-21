package com.revup.question.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class InvalidQuestionTypeException extends AppException {
    public InvalidQuestionTypeException() {
        super(ErrorCode.QUESTION_INVALID_TYPE);
    }
}
