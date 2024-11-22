package com.revup.error;

public class InfraException extends AppException {

    public InfraException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessageTemplate());
    }
}
