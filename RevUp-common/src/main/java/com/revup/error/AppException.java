package com.revup.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AppException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String formattedMessage;

    public AppException(ErrorCode errorCode,Object... args){
        super(String.format(errorCode.getMessageTemplate(), args));
        this.errorCode = errorCode;
        this.formattedMessage = String.format(errorCode.getMessageTemplate(), args);
    }

    @Override
    public String getMessage(){
        return formattedMessage;
    }
}
