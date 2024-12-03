package com.revup.heart.exception;

import com.revup.error.AppException;
import com.revup.error.ErrorCode;

public class HeartNotFoundException extends AppException {
    public HeartNotFoundException(Long id) {
        super(ErrorCode.HEART_NOT_FOUND, id);
    }
}
