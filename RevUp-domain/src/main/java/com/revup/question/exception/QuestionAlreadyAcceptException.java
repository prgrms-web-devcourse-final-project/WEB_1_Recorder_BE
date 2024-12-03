package com.revup.question.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class QuestionAlreadyAcceptException extends AppException {
    public QuestionAlreadyAcceptException() {
        super(ErrorCode.QUESTION_ALREADY_ACCEPT);
    }
}
