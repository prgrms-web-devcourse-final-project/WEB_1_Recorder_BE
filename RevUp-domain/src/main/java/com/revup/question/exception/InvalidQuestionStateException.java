package com.revup.question.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class InvalidQuestionStateException extends AppException {
    public InvalidQuestionStateException() {
        super(ErrorCode.QUESTION_INVALID_STATE);
    }
}
