package com.revup.exception;

import com.revup.error.SecurityException;

import static com.revup.error.ErrorCode.TOKEN_INVALID;
import static com.revup.error.ErrorCode.TOKEN_NOT_EXIST;

public class NotFoundTokenException extends SecurityException {

    public static final SecurityException EXCEPTION = new NotFoundTokenException();
    private NotFoundTokenException() {
        super(TOKEN_NOT_EXIST);
    }
}
