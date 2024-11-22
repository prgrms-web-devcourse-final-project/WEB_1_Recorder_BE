package com.revup.error;

public class SecurityException extends AppException {

    public SecurityException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessageTemplate());
    }
}
