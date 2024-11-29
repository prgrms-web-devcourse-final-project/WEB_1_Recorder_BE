package com.revup.answer.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class InvalidAdoptedReviewException extends AppException {
    public InvalidAdoptedReviewException() {
        super(ErrorCode.ADOPTED_REVIEW_INVALID);
    }
}
