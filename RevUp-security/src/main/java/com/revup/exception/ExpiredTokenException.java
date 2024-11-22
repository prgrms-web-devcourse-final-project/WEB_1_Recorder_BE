package com.revup.exception;

import com.revup.error.SecurityException;

import static com.revup.error.ErrorCode.TOKEN_TIMEOUT;

public class ExpiredTokenException extends SecurityException {

    public static final SecurityException EXCEPTION = new ExpiredTokenException();
    private ExpiredTokenException() {
        super(TOKEN_TIMEOUT);
    }
}
