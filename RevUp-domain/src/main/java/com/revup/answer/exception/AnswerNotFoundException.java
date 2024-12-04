package com.revup.answer.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class AnswerNotFoundException extends AppException {
    public AnswerNotFoundException(Long id) {
        super(ErrorCode.ANSWER_NOT_FOUND,id);
    }
}
