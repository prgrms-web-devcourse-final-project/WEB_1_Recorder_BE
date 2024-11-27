package com.revup.answer.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class AnswerCreationConcurrencyException extends AppException {
    public AnswerCreationConcurrencyException() {
        super(ErrorCode.ANSWER_CONCURRENCY);
    }
}
