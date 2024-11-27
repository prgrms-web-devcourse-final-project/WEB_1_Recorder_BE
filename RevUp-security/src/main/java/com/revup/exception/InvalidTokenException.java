package com.revup.exception;

import com.revup.error.SecurityException;

import static com.revup.error.ErrorCode.TOKEN_INVALID;

public class InvalidTokenException extends SecurityException {

    public static final SecurityException EXCEPTION = new InvalidTokenException();
    private InvalidTokenException() {
        super(TOKEN_INVALID);
    }
}
