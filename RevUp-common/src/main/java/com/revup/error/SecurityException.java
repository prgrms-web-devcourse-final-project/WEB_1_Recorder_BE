package com.revup.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage", "message"})
public class SecurityException extends AppException {

    public SecurityException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessageTemplate());
    }
}
