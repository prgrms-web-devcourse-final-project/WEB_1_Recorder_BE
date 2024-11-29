package com.revup.answer.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class AnswerAlreadyAdoptedException extends AppException {
    public AnswerAlreadyAdoptedException() {
        super(ErrorCode.ANSWER_ALREADY_ADOPTED);
    }
}
