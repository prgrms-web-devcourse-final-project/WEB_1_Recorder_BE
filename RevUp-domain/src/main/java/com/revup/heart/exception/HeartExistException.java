package com.revup.heart.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class HeartExistException extends AppException {
    public HeartExistException() {
        super(ErrorCode.HEART_ALREADY_EXIST);
    }
}
