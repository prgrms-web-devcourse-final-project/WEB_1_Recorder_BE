package com.revup.heart.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class InvalidHeartTypeException extends AppException {
    public InvalidHeartTypeException() {
        super(ErrorCode.HEART_TYPE_INVALID);
    }
}
