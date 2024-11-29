package com.revup.answer.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class AnswerNotLinkedException extends AppException {
    public AnswerNotLinkedException() {
        super(ErrorCode.ANSWER_NOT_LINKED);
    }
}
